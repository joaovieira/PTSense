package com.cloud2bubble.ptsense;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

	public static LinkedList<Float> lightValues; // lux units
	public static LinkedList<Float> accelerationsX, accelerationsY,
			accelerationsZ; // ms2
	public static LinkedList<Float> pressureValues; // hPa (millibar)
	public static LinkedList<Float> relHumidityValues;// percent %
	public static LinkedList<Float> ambTemperatureValues; // celsius �C
	
	Float x, y, z;

	Float avgLight, avgAccelX, avgAccelY, avgAccelZ, avgPressure, avgHumidity,
			avgTemperature;

	SensorDatabaseHandler sensorDatabase;

	public static final String BROADCAST_ACTION = "com.cloud2bubble.ptsense.displayevent";
	private final Handler handler = new Handler();
	Intent intent;

	@Override
	public void onCreate() {
		super.onCreate();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// sensorDatabase = new SensorDatabaseHandler(this);

		lightValues = new LinkedList<Float>();
		pressureValues = new LinkedList<Float>();
		relHumidityValues = new LinkedList<Float>();
		ambTemperatureValues = new LinkedList<Float>();
		accelerationsX = new LinkedList<Float>(Arrays.asList(0.0f));
		accelerationsY = new LinkedList<Float>(Arrays.asList(0.0f));
		accelerationsZ = new LinkedList<Float>(Arrays.asList(0.0f));
		avgLight = avgAccelX = avgAccelY = avgAccelZ = avgPressure = avgHumidity = avgTemperature = 0.0f;
		x = y = z = 0.0f;

		intent = new Intent(BROADCAST_ACTION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IS_RUNNING = true;

		startOnGoingNotification();
		registerSensorListeners();
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
		if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
			mAcceleration = sensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(this, mAcceleration,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
			mAmbTemperature = sensorManager
					.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
			sensorManager.registerListener(this, mAmbTemperature,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
			mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
			sensorManager.registerListener(this, mLight,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
			mPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
			sensorManager.registerListener(this, mPressure,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
			mProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
			sensorManager.registerListener(this, mProximity,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
			mRelHumidity = sensorManager
					.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
			sensorManager.registerListener(this, mRelHumidity,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	private void unregisterSensorListeners() {
		sensorManager.unregisterListener(this);
	}

	private void collectDataFromSensors() {
		// print values on Sensing Now activity UI every 2 seconds
		handler.removeCallbacks(sendUpdatesToUI);
		handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

		// calculate average and insert store into database every 20 seconds
		sensorThread = new PreProcessThread();
		sensorThread.start();
	}

	private void stop() {
		unregisterSensorListeners();
		handler.removeCallbacks(sendUpdatesToUI);
		stopForeground(true);

		// Tell the user we stopped.
		Toast.makeText(this, "Sensing stopped", Toast.LENGTH_SHORT).show();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do something here if sensor accuracy changes.
	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				float dX = event.values[0] - x;
				x = event.values[0];
				accelerationsX.add(dX);
				float dY = event.values[1] - y;
				y = event.values[1];
				accelerationsY.add(dY);
				float dZ = event.values[2] - z;
				z = event.values[2];
				accelerationsZ.add(dZ);
				break;
			case Sensor.TYPE_AMBIENT_TEMPERATURE:
				ambTemperatureValues.add(Float.valueOf(event.values[0]));
				break;
			case Sensor.TYPE_LIGHT:
				lightValues.add(Float.valueOf(event.values[0]));
				break;
			case Sensor.TYPE_PRESSURE:
				pressureValues.add(Float.valueOf(event.values[0]));
				break;
			case Sensor.TYPE_RELATIVE_HUMIDITY:
				relHumidityValues.add(Float.valueOf(event.values[0]));
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
			handler.postDelayed(this, 1000); // 2 seconds
		}

		private void sendIntentWithSensorData() {
			if (mAcceleration != null) {
				intent.putExtra("accelX",
						String.valueOf(accelerationsX.getLast()));
				intent.putExtra("accelY",
						String.valueOf(accelerationsY.getLast()));
				intent.putExtra("accelZ",
						String.valueOf(accelerationsZ.getLast()));
			}

			if (mAmbTemperature != null)
				intent.putExtra("temperature",
						String.valueOf(ambTemperatureValues.getLast()));

			if (mLight != null)
				intent.putExtra("light", String.valueOf(lightValues.getLast()));

			if (mPressure != null)
				intent.putExtra("pressure",
						String.valueOf(pressureValues.getLast()));

			if (mRelHumidity != null)
				intent.putExtra("humidity",
						String.valueOf(relHumidityValues.getLast()));

			sendBroadcast(intent);
		}
	};

	private class PreProcessThread extends Thread {
		@Override
		public void run() {
			while (IS_RUNNING) {
				try {
					sleep(20000);
					Log.d("SmartphoneSensingService",
							"Starting to update SensorDatabase");

					/*
					 * String currentTime = getCurrentTimeStamp(); SensorData
					 * data = new SensorData(currentTime);
					 * 
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
					 * 
					 * clearBuffers(); Log.d("SmartphoneSensingService",
					 * "Finished updating SensorDatabase");
					 */

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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

		private void clearBuffers() {
			lightValues.clear();
			accelerationsX.clear();
			accelerationsY.clear();
			accelerationsZ.clear();
			pressureValues.clear();
			relHumidityValues.clear();
			ambTemperatureValues.clear();
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
