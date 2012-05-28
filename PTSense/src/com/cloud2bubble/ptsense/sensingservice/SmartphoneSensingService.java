package com.cloud2bubble.ptsense.sensingservice;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.cloud2bubble.ptsense.PTSense;
import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.Home;
import com.cloud2bubble.ptsense.activity.Sensing;
import com.cloud2bubble.ptsense.activity.Settings;
import com.cloud2bubble.ptsense.component.MultiSelectListPreference;
import com.cloud2bubble.ptsense.database.SensorData;
import com.cloud2bubble.ptsense.servercommunication.C2BClient;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

public class SmartphoneSensingService extends Service implements
		SensorEventListener {

	public static Sensor mAcceleration = null, mLight = null, mPressure = null,
			mProximity = null;
	public static MediaRecorder soundRecorder = null;
	public static LocationSystem locationSystem = null;

	private static ArrayBlockingQueue<Float> lightValues, accelerationsX,
			accelerationsY, accelerationsZ, pressureValues;
	private static ArrayBlockingQueue<Double> soundValues;

	private static List<Float> tmpLightValues, tmpAccelerationsX,
			tmpAccelerationsY, tmpAccelerationsZ, tmpPressureValues;
	private static List<Double> tmpSoundValues;

	private static Float currentX, currentdX, currentY, currentdY, currentZ,
			currentdZ, currentLight, currentPressure, currentProximity;

	public static final String BROADCAST_ACTION = "com.cloud2bubble.ptsense.DISPLAYEVENT";
	private final Handler handler = new Handler();
	Intent uiIntent;

	private final String outputFile = "PTSense_output";

	PTSense app;
	long tripId;

	@Override
	public void onCreate() {
		super.onCreate();
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		app = (PTSense) getApplication();

		SharedPreferences prefs = Settings.getPrefs(this);
		String persistedPrefs = prefs.getString("smartphone_sensors", null);
		List<String> sensorsAllowed = Arrays.asList(MultiSelectListPreference
				.fromPersistedPreferenceValue(persistedPrefs));

		Log.d("SmartphoneSensingService", "smartphone_sensors pref value:"
				+ sensorsAllowed.toString());

		if (sensorsAllowed != null) {
			if (sensorsAllowed
					.contains(getString(R.string.sensordata_key_acceleration)))
				mAcceleration = sensorManager
						.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

			if (sensorsAllowed
					.contains(getString(R.string.sensordata_key_light)))
				mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

			if (sensorsAllowed
					.contains(getString(R.string.sensordata_key_pressure)))
				mPressure = sensorManager
						.getDefaultSensor(Sensor.TYPE_PRESSURE);

			if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
				mProximity = sensorManager
						.getDefaultSensor(Sensor.TYPE_PROXIMITY);

			if (sensorsAllowed
					.contains(getString(R.string.sensordata_key_sound))) {

				soundRecorder = new MediaRecorder();
				File sampleDir = Environment.getExternalStorageDirectory();
				String soundOutputPath = sampleDir + File.separator
						+ outputFile + ".3gp";

				soundRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				soundRecorder
						.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				soundRecorder
						.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
				soundRecorder.setOutputFile(soundOutputPath);
			}

			if (sensorsAllowed.contains(getString(R.string.gps)))
				locationSystem = new LocationSystem(this);
		}

		lightValues = new ArrayBlockingQueue<Float>(15); // 200ms * 2sec = 10
															// values
		pressureValues = new ArrayBlockingQueue<Float>(15);
		accelerationsX = new ArrayBlockingQueue<Float>(15);
		accelerationsY = new ArrayBlockingQueue<Float>(15);
		accelerationsZ = new ArrayBlockingQueue<Float>(15);
		soundValues = new ArrayBlockingQueue<Double>(15);

		tmpLightValues = new ArrayList<Float>(15);
		tmpPressureValues = new ArrayList<Float>(15);
		tmpAccelerationsX = new ArrayList<Float>(15);
		tmpAccelerationsY = new ArrayList<Float>(15);
		tmpAccelerationsZ = new ArrayList<Float>(15);
		tmpSoundValues = new ArrayList<Double>(15);

		currentX = currentY = currentZ = currentLight = currentdX = currentdY
				= currentdZ = currentPressure = currentProximity = 0.0f;

		uiIntent = new Intent(BROADCAST_ACTION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startOnGoingNotification();
		registerSensorListeners();
		startSoundRecording();
		collectDataFromSensors();

		tripId = app.getCurrentTripId();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		unregisterSensorListeners();
		stopSoundRecording();
		stopForeground(true);

		Toast.makeText(SmartphoneSensingService.this, "Sensing stopped",
				Toast.LENGTH_SHORT).show();

		app.updateTripEndTime(new GregorianCalendar());
		sendTripToServer();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private void startOnGoingNotification() {
		Resources res = this.getResources();
		NotificationCompat.Builder feedbackBuilder = new NotificationCompat.Builder(
				this);
		feedbackBuilder
				.setSmallIcon(R.drawable.ic_stat_sensing)
				.setContentTitle(
						res.getString(R.string.notification_sensing_title))
				.setContentText(
						res.getString(R.string.notification_sensing_message))
				.setTicker(getText(R.string.notification_sensing_ticker_text))
				.setWhen(System.currentTimeMillis())
				.setContentIntent(
						TaskStackBuilder
								.from(this)
								.addParentStack(Home.class)
								.addNextIntent(
										new Intent(this, Sensing.class)
												.putExtra("tab", 1))
								.getPendingIntent(0, 0));

		startForeground(PTSense.ONGOING_NOTIFICATION,
				feedbackBuilder.getNotification());
	}

	private void registerSensorListeners() {
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (mAcceleration != null)
			sensorManager.registerListener(this, mAcceleration,
					SensorManager.SENSOR_DELAY_NORMAL);

		if (mLight != null)
			sensorManager.registerListener(this, mLight,
					SensorManager.SENSOR_DELAY_NORMAL);

		if (mLight != null)
			sensorManager.registerListener(this, mPressure,
					SensorManager.SENSOR_DELAY_NORMAL);

		if (mProximity != null)
			sensorManager.registerListener(this, mProximity,
					SensorManager.SENSOR_DELAY_NORMAL);

		if (locationSystem != null)
			locationSystem.start();
	}

	private void unregisterSensorListeners() {
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.unregisterListener(this);

		if (locationSystem != null)
			locationSystem.stop();
	}

	private void collectDataFromSensors() {
		handler.removeCallbacks(sendUpdatesToUI);
		handler.postDelayed(sendUpdatesToUI, 200);

		// calculate average and insert store into database every 2 seconds
		Thread sensorThread = new PreProcessThread();
		handler.postDelayed(sensorThread, 1000);
	}

	private void startSoundRecording() {
		if (soundRecorder != null) {
			try {
				soundRecorder.prepare();
				soundRecorder.start();
				soundRecorder.getMaxAmplitude();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void stopSoundRecording() {
		if (soundRecorder != null) {
			soundRecorder.stop();
			soundRecorder.release();
		}
	}

	private void sendTripToServer() {
		Intent i = new Intent(this, C2BClient.class);
		i.putExtra("trip_id", tripId);
		startService(i);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				float dX = event.values[0] - currentX;
				currentX = event.values[0];
				currentdX = dX;
				accelerationsX.offer(currentX);
				float dY = event.values[1] - currentY;
				currentY = event.values[1];
				currentdY = dY;
				accelerationsY.offer(currentY);
				float dZ = event.values[2] - currentZ;
				currentZ = event.values[2];
				currentdZ = dZ;
				accelerationsZ.offer(currentZ);
				break;
			case Sensor.TYPE_LIGHT:
				currentLight = event.values[0];
				lightValues.offer(currentLight);
				break;
			case Sensor.TYPE_PRESSURE:
				currentPressure = event.values[0];
				pressureValues.offer(currentPressure);
				break;
			case Sensor.TYPE_PROXIMITY:
				// TODO cancel readings from light, humidity, pressure when
				// close to things for more than 10sec
				currentProximity = event.values[0];
				break;
			}
		}
	};

	private Runnable sendUpdatesToUI = new Runnable() {
		DecimalFormat df = new DecimalFormat("0.0000");
		DecimalFormat dfc = new DecimalFormat("0.00000000");

		public void run() {
			if (app.isSensing()) {
				sendIntentWithSensorData();
				handler.postDelayed(this, 500); // 0.5 second
			}
		}

		private void sendIntentWithSensorData() {
			if (soundRecorder != null) {
				double amp = getPowerDB();
				soundValues.offer(Double.valueOf(amp));
				uiIntent.putExtra("sound", df.format(amp));
			}

			if (mAcceleration != null) {
				String oscilation = "x:" + df.format(currentdX) + " y:"
						+ df.format(currentdY) + " z:" + df.format(currentdZ);
				uiIntent.putExtra("oscilation", oscilation);
			}

			if (mLight != null)
				uiIntent.putExtra("light", df.format(currentLight));

			if (mPressure != null)
				uiIntent.putExtra("pressure", df.format(currentPressure));

			if (locationSystem != null) {
				String position = formatCoordinates(locationSystem
						.getLocation());
				uiIntent.putExtra("position", position);
			}

			sendBroadcast(uiIntent);
		}

		private String formatCoordinates(Location location) {
			if (location == null) {
				return getString(R.string.no_position);
			} else {
				return "Lat:" + dfc.format(location.getLatitude()) + " Lon:"
						+ dfc.format(location.getLongitude());
			}
		}

		private double getPowerDB() {
			double power_db = 20 * Math.log10(soundRecorder.getMaxAmplitude()
					/ MediaRecorder.getAudioSourceMax());
			return power_db;
		}
	};

	private class PreProcessThread extends Thread {
		@Override
		public void run() {
			if (app.isSensing()) {
				updateSensorDatabase();
				handler.postDelayed(this, 2000);
			}
		}

		private void updateSensorDatabase() {
			// Log.d("SmartphoneSensingService", "Updating SensorDatabase");

			drainBuffers();

			String currentTime = getCurrentTimeStamp();
			SensorData data = new SensorData(tripId, currentTime);

			Float accelFinal = DataProcessor.processAccelerometerData(
					tmpAccelerationsX, tmpAccelerationsY, tmpAccelerationsZ);
			data.addData(getString(R.string.sensordata_key_acceleration),
					accelFinal);

			Float lightFinal = DataProcessor.processLightData(tmpLightValues);
			data.addData(getString(R.string.sensordata_key_light), lightFinal);

			Float pressureFinal = DataProcessor
					.processPressureData(tmpPressureValues);
			data.addData(getString(R.string.sensordata_key_pressure),
					pressureFinal);

			Float soundFinal = DataProcessor.processSoundData(tmpSoundValues);
			data.addData(getString(R.string.sensordata_key_sound), soundFinal);

			if (locationSystem != null) {
				Pair<Float, Float> coordinates = DataProcessor
						.processCoordinate(locationSystem.getLocation());
				data.addData(getString(R.string.sensordata_key_latitude),
						coordinates.first);
				data.addData(getString(R.string.sensordata_key_longitude),
						coordinates.second);
			}

			data.addData(getString(R.string.sensordata_key_proximity),
					currentProximity);

			app.getDatabase().addSensorData(data);

			clearTempBuffers();
			// TODO define listener to reset file when reached max file size
		}

		private void clearTempBuffers() {
			tmpLightValues.clear();
			tmpAccelerationsX.clear();
			tmpAccelerationsY.clear();
			tmpAccelerationsZ.clear();
			tmpPressureValues.clear();
			tmpSoundValues.clear();
		}

		private void drainBuffers() {
			lightValues.drainTo(tmpLightValues);
			accelerationsX.drainTo(tmpAccelerationsX);
			accelerationsY.drainTo(tmpAccelerationsY);
			accelerationsZ.drainTo(tmpAccelerationsZ);
			pressureValues.drainTo(tmpPressureValues);
			soundValues.drainTo(tmpSoundValues);
		}

		private String getCurrentTimeStamp() {
			SimpleDateFormat sdfDate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			return strDate;
		}
	}
}
