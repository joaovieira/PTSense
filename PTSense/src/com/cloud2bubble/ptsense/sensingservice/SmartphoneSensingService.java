package com.cloud2bubble.ptsense.sensingservice;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.Sensing;
import com.cloud2bubble.ptsense.database.SensorData;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.list.ReviewItem;
import com.cloud2bubble.ptsense.servercommunication.C2BClient;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SmartphoneSensingService extends Service implements
		SensorEventListener {

	private static final int ONGOING_NOTIFICATION = 10;
	public static boolean IS_RUNNING;

	private Thread sensorThread = null;
	private SensorManager sensorManager;
	public static Sensor mAcceleration, mAmbTemperature, mLight, mPressure,
			mProximity, mRelHumidity = null;
	public static MediaRecorder soundRecorder = null;
	String soundOutputPath;

	private ArrayBlockingQueue<Float> lightValues, accelerationsX,
			accelerationsY, accelerationsZ, pressureValues, relHumidityValues,
			ambTemperatureValues;
	private ArrayBlockingQueue<Double> soundValues;

	private List<Float> tmpLightValues, tmpAccelerationsX, tmpAccelerationsY,
			tmpAccelerationsZ, tmpPressureValues, tmpRelHumidityValues,
			tmpAmbTemperatureValues;
	private List<Double> tmpSoundValues;

	private Float currentX, currentdX, currentY, currentdY, currentZ,
			currentdZ, currentLight, currentPressure, currentTemp,
			currentHumidity;

	public static final String BROADCAST_ACTION = "com.cloud2bubble.ptsense.displayevent";
	private final Handler handler = new Handler();
	Intent uiIntent;

	private final String outputFile = "PTSense_output";
	
	private DatabaseHandler database;
	ReviewItem trip;
	long tripId;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		database = DatabaseHandler.getInstance(this);

		if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
			mAcceleration = sensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
			mAmbTemperature = sensorManager
					.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

		if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
			mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
			mPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

		if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
			mProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

		if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
			mRelHumidity = sensorManager
					.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

		soundRecorder = new MediaRecorder();
		File sampleDir = Environment.getExternalStorageDirectory();
		soundOutputPath = sampleDir + File.separator + outputFile + ".3gp";
		setupSoundRecorder();

		lightValues = new ArrayBlockingQueue<Float>(60); // 200ms * 10sec = 50
															// values
		pressureValues = new ArrayBlockingQueue<Float>(60);
		relHumidityValues = new ArrayBlockingQueue<Float>(60);
		ambTemperatureValues = new ArrayBlockingQueue<Float>(60);
		accelerationsX = new ArrayBlockingQueue<Float>(60);
		accelerationsY = new ArrayBlockingQueue<Float>(60);
		accelerationsZ = new ArrayBlockingQueue<Float>(60);
		soundValues = new ArrayBlockingQueue<Double>(60);

		tmpLightValues = new ArrayList<Float>(60);
		tmpPressureValues = new ArrayList<Float>(60);
		tmpRelHumidityValues = new ArrayList<Float>(60);
		tmpAmbTemperatureValues = new ArrayList<Float>(60);
		tmpAccelerationsX = new ArrayList<Float>(60);
		tmpAccelerationsY = new ArrayList<Float>(60);
		tmpAccelerationsZ = new ArrayList<Float>(60);
		tmpSoundValues = new ArrayList<Double>(60);

		currentX = currentY = currentZ = currentLight = currentPressure = currentTemp = currentHumidity = 0.0f;

		uiIntent = new Intent(BROADCAST_ACTION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		createTrip(intent);
		startOnGoingNotification();
		registerSensorListeners();
		startSoundRecording();
		collectDataFromSensors();

		IS_RUNNING = true;
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	private void createTrip(Intent intent) {
		Bundle tripInfo = intent.getBundleExtra("trip_info");
		String service = tripInfo.getString("service");
		String line = tripInfo.getString("line");
		String origin = tripInfo.getString("origin");
		String destination = tripInfo.getString("destination");
		trip = new ReviewItem(line, service, origin, destination, new GregorianCalendar(), null);
		tripId = saveTripToDatabase();
		trip.setDatabaseId(tripId);
	}

	@Override
	public void onDestroy() {
		stop();
		IS_RUNNING = false;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@SuppressWarnings("deprecation")
	private void startOnGoingNotification() {
		// TODO later update: use Notification.Builder after Api 11...
		Notification notification = new Notification(
				R.drawable.ic_stat_sensing,
				getText(R.string.notification_sensing_ticker_text),
				System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, Sensing.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(this,
				getText(R.string.notification_sensing_title),
				getText(R.string.notification_sensing_message), pendingIntent);
		startForeground(ONGOING_NOTIFICATION, notification);
	}

	private void registerSensorListeners() {
		if (mAcceleration != null)
			sensorManager.registerListener(this, mAcceleration,
					SensorManager.SENSOR_DELAY_NORMAL);

		if (mAmbTemperature != null)
			sensorManager.registerListener(this, mAmbTemperature,
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

		if (mRelHumidity != null)
			sensorManager.registerListener(this, mRelHumidity,
					SensorManager.SENSOR_DELAY_NORMAL);
	}

	private void unregisterSensorListeners() {
		sensorManager.unregisterListener(this);
	}

	private void collectDataFromSensors() {
		// print values on Sensing Now activity UI every 2 seconds
		handler.removeCallbacks(sendUpdatesToUI);
		handler.postDelayed(sendUpdatesToUI, 200); // 1 second

		// calculate average and insert store into database every 20 seconds
		sensorThread = new PreProcessThread();
		sensorThread.start();
	}

	private void stop() {
		unregisterSensorListeners();
		stopSoundRecording();

		handler.removeCallbacks(sendUpdatesToUI);
		stopForeground(true);
		
		updateTripDate();
		startServerCommunication(tripId);

		// Tell the user we stopped.
		Toast.makeText(this, "Sensing stopped", Toast.LENGTH_SHORT).show();
	}

	private void updateTripDate() {
		trip.setEndTime(new GregorianCalendar());
		database.updatePendingReview(trip);
	}

	private void startServerCommunication(long id) {
		Intent i = new Intent(this, C2BClient.class);
		i.putExtra("trip_id", id);
		startService(i);
	}

	private long saveTripToDatabase() {
		return database.addPendingReview(trip);
	}

	private void setupSoundRecorder() {
		soundRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		soundRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		soundRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		soundRecorder.setOutputFile(soundOutputPath);
	}

	private void startSoundRecording() {
		try {
			soundRecorder.prepare();
			soundRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopSoundRecording() {
		soundRecorder.stop();
		soundRecorder.release();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do something here if sensor accuracy changes.
	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				float dX = event.values[0] - currentX;
				currentX = event.values[0];
				currentdX = dX;
				accelerationsX.offer(dX);
				float dY = event.values[1] - currentY;
				currentY = event.values[1];
				currentdY = dY;
				accelerationsY.offer(dY);
				float dZ = event.values[2] - currentZ;
				currentZ = event.values[2];
				currentdZ = dZ;
				accelerationsZ.offer(dZ);
				break;
			case Sensor.TYPE_AMBIENT_TEMPERATURE:
				currentTemp = event.values[0];
				ambTemperatureValues.offer(currentTemp);
				break;
			case Sensor.TYPE_LIGHT:
				currentLight = event.values[0];
				lightValues.offer(currentLight);
				break;
			case Sensor.TYPE_PRESSURE:
				currentPressure = event.values[0];
				pressureValues.offer(currentPressure);
				break;
			case Sensor.TYPE_RELATIVE_HUMIDITY:
				currentHumidity = event.values[0];
				relHumidityValues.offer(currentHumidity);
				break;
			case Sensor.TYPE_PROXIMITY:
				// TODO cancel readings from light, humidity, pressure when
				// close to things for more than 10sec
				break;
			}
		}
	};

	private Runnable sendUpdatesToUI = new Runnable() {
		public void run() {
			sendIntentWithSensorData();
			handler.postDelayed(this, 1000); // 1 second
		}

		private void sendIntentWithSensorData() {
			if (soundRecorder != null) {
				double amp = getPowerDB();
				soundValues.offer(Double.valueOf(amp));
				uiIntent.putExtra("sound", String.valueOf(amp));
			}

			if (mAcceleration != null) {
				String oscilation = "x:" + String.valueOf(currentdX) + " y:"
						+ String.valueOf(currentdY) + " z:"
						+ String.valueOf(currentdZ);
				uiIntent.putExtra("oscilation", oscilation);
			}

			if (mAmbTemperature != null)
				uiIntent.putExtra("temperature", String.valueOf(currentTemp));

			if (mLight != null)
				uiIntent.putExtra("light", String.valueOf(currentLight));

			if (mPressure != null)
				uiIntent.putExtra("pressure", String.valueOf(currentPressure));

			if (mRelHumidity != null)
				uiIntent.putExtra("humidity", String.valueOf(currentHumidity));

			sendBroadcast(uiIntent);
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
			while (IS_RUNNING) {
				try {
					sleep(2000);
					Log.d("SmartphoneSensingService", "Updating SensorDatabase");

					drainBuffers();

					String currentTime = getCurrentTimeStamp();
					SensorData data = new SensorData(tripId, currentTime);

					Float accelFinal = DataProcessor.processAccelerometerData(accelerationsX, accelerationsY, accelerationsZ);
					data.addData(getString(R.string.sensordata_key_acceleration), accelFinal);
					
					Float tempFinal = DataProcessor.processTemperatureData(ambTemperatureValues);
					data.addData(getString(R.string.sensordata_key_temperature), tempFinal);
					
					Float lightFinal = DataProcessor.processLightData(ambTemperatureValues);
					data.addData(getString(R.string.sensordata_key_light), lightFinal);
					
					Float pressureFinal = DataProcessor.processPressureData(ambTemperatureValues);
					data.addData(getString(R.string.sensordata_key_pressure), pressureFinal);
					
					Float humidityFinal = DataProcessor.processHumidityData(ambTemperatureValues);
					data.addData(getString(R.string.sensordata_key_humidity), humidityFinal);
					
					Float soundFinal = DataProcessor.processSoundData(ambTemperatureValues);
					data.addData(getString(R.string.sensordata_key_sound), soundFinal);
					
					database.addSensorData(data);
					
					clearTempBuffers();
					// TODO define listener to reset file when reached max file
					// size

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void clearTempBuffers() {
			tmpLightValues.clear();
			tmpAccelerationsX.clear();
			tmpAccelerationsY.clear();
			tmpAccelerationsY.clear();
			tmpPressureValues.clear();
			tmpPressureValues.clear();
			tmpAmbTemperatureValues.clear();
			tmpSoundValues.clear();
		}

		private void drainBuffers() {
			lightValues.drainTo(tmpLightValues);
			accelerationsX.drainTo(tmpAccelerationsX);
			accelerationsY.drainTo(tmpAccelerationsY);
			accelerationsZ.drainTo(tmpAccelerationsZ);
			pressureValues.drainTo(tmpPressureValues);
			relHumidityValues.drainTo(tmpRelHumidityValues);
			ambTemperatureValues.drainTo(tmpAmbTemperatureValues);
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
