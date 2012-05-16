package com.cloud2bubble.ptsense.sensingservice;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ArrayBlockingQueue;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.Sensing;
import com.cloud2bubble.ptsense.database.SensorData;
import com.cloud2bubble.ptsense.database.DatabaseHandler;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SmartphoneSensingService extends Service implements
		SensorEventListener {

	private static final int ONGOING_NOTIFICATION = 1;
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

	private DatabaseHandler database;

	public static final String BROADCAST_ACTION = "com.cloud2bubble.ptsense.displayevent";
	private final Handler handler = new Handler();
	Intent intent;

	private final String outputFile = "PTSense_output";

	@Override
	public void onCreate() {
		super.onCreate();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// sensorDatabase = new SensorDatabaseHandler(this);

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

		intent = new Intent(BROADCAST_ACTION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IS_RUNNING = true;

		startOnGoingNotification();
		registerSensorListeners();
		startSoundRecording();
		collectDataFromSensors();

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
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
		handler.postDelayed(sendUpdatesToUI, 500); // 1 second

		// calculate average and insert store into database every 20 seconds
		sensorThread = new PreProcessThread();
		sensorThread.start();
	}

	private void stop() {
		unregisterSensorListeners();
		stopSoundRecording();

		handler.removeCallbacks(sendUpdatesToUI);
		stopForeground(true);

		// Tell the user we stopped.
		Toast.makeText(this, "Sensing stopped", Toast.LENGTH_SHORT).show();
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
				intent.putExtra("sound", String.valueOf(amp));
			}

			if (mAcceleration != null) {
				String oscilation = "x:" + String.valueOf(currentdX) + " y:"
						+ String.valueOf(currentdY) + " z:"
						+ String.valueOf(currentdZ);
				intent.putExtra("oscilation", oscilation);
			}

			if (mAmbTemperature != null)
				intent.putExtra("temperature", String.valueOf(currentTemp));

			if (mLight != null)
				intent.putExtra("light", String.valueOf(currentLight));

			if (mPressure != null)
				intent.putExtra("pressure", String.valueOf(currentPressure));

			if (mRelHumidity != null)
				intent.putExtra("humidity", String.valueOf(currentHumidity));

			sendBroadcast(intent);
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
					sleep(20000);
					Log.d("SmartphoneSensingService", "Updating SensorDatabase");

					drainBuffers();

					String currentTime = getCurrentTimeStamp();
					SensorData data = new SensorData(currentTime);

					/*
					 * data.setType("LIGHT");
					 * data.setData(getAverageData(lightValues));
					 * sensorDatabase.addSensorData(data);
					 * Log.d("SmartphoneSensingService",
					 * "Updated database with: LIGHT " +
					 * getAverageData(lightValues));
					 * data.setType("ACCELERATION_X");
					 * data.setData(getAverageData(accelerationsX));
					 * sensorDatabase.addSensorData(data);
					 * Log.d("SmartphoneSensingService",
					 * "Updated database with: ACCELERATION_X " +
					 * getAverageData(accelerationsX));
					 * data.setType("ACCELERATION_Y");
					 * data.setData(getAverageData(accelerationsY));
					 * sensorDatabase.addSensorData(data);
					 * Log.d("SmartphoneSensingService",
					 * "Updated database with: ACCELERATION_Y " +
					 * getAverageData(accelerationsY));
					 * data.setType("ACCELERATION_Z");
					 * data.setData(getAverageData(accelerationsZ));
					 * sensorDatabase.addSensorData(data);
					 * Log.d("SmartphoneSensingService",
					 * "Updated database with: ACCELERATION_Z " +
					 * getAverageData(accelerationsZ));
					 * data.setType("PRESSURE");
					 * data.setData(getAverageData(pressureValues));
					 * sensorDatabase.addSensorData(data);
					 * Log.d("SmartphoneSensingService",
					 * "Updated database with: PRESSURE " +
					 * getAverageData(pressureValues));
					 * data.setType("HUMIDITY");
					 * data.setData(getAverageData(relHumidityValues));
					 * sensorDatabase.addSensorData(data);
					 * Log.d("SmartphoneSensingService",
					 * "Updated database with: HUMIDITY " +
					 * getAverageData(relHumidityValues));
					 * data.setType("TEMPERATURE");
					 * data.setData(getAverageData(ambTemperatureValues));
					 * sensorDatabase.addSensorData(data);
					 * Log.d("SmartphoneSensingService",
					 * "Updated database with: TEMPERATURE " +
					 * getAverageData(ambTemperatureValues));
					 */
					// restartRecording();
					// TODO define listener to reset file when reached max file
					// size

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void restartRecording() {
			stopSoundRecording();
			setupSoundRecorder();
			startSoundRecording();
		}

		private Float getAverageData(LinkedList<Float> valuesList) {
			ListIterator<Float> iter = valuesList.listIterator();
			int size = valuesList.size();
			Float average = new Float(0);

			while (iter.hasNext()) {
				average += iter.next();
			}
			return average / size;
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
