package com.cloud2bubble.ptsense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SmartphoneSensingService extends Service implements
		SensorEventListener {

	private static final int ONGOING_NOTIFICATION = 1;
	public static boolean IS_RUNNING;

	private Thread sensorThread = null;
	private SensorManager sensorManager;
	private Sensor mLinearAcceleration, mAmbTemperature, mLight, mPressure,
			mProximity, mRelHumidity = null;

	public static ArrayList<Float> lightValues; //lux units
	public static ArrayList<Float> accelerationsX, accelerationsY, accelerationsZ; //ms2
	public static ArrayList<Float> pressureValues; //hPa (millibar)
	public static ArrayList<Float> relHumidityValues;//percent %
	public static ArrayList<Float> ambTemperatureValues; //celsius ¼C
	
	Float avgLight, avgAccelX, avgAccelY, avgAccelZ, avgPressure, avgHumidity, avgTemperature;
	
	SensorDatabaseHandler sensorDatabase;

	public SmartphoneSensingService() {
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorDatabase = new SensorDatabaseHandler(this);
		
		lightValues = pressureValues = relHumidityValues = ambTemperatureValues = new ArrayList<Float>();
		accelerationsX = accelerationsY = accelerationsZ = new ArrayList<Float>();
		avgLight = avgAccelX = avgAccelY = avgAccelZ = 
				avgPressure = avgHumidity = avgTemperature = new Float(0);
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
		if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
			mLinearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			sensorManager.registerListener(this, mLinearAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
			mAmbTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
			sensorManager.registerListener(this, mAmbTemperature, SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
			mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
			sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
			mPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
			sensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
			mProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
			sensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
			mRelHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
			sensorManager.registerListener(this, mRelHumidity, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	private void unregisterSensorListeners() {
		sensorManager.unregisterListener(this);
	}

	private void collectDataFromSensors() {
		sensorThread = new PreProcessThread();
		sensorThread.start();
	}

	private void stop() {
		unregisterSensorListeners();
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
			case Sensor.TYPE_LINEAR_ACCELERATION:
				accelerationsX.add(Float.valueOf(event.values[0]));
				accelerationsY.add(Float.valueOf(event.values[1]));
				accelerationsZ.add(Float.valueOf(event.values[2]));
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
				// TODO cancel readings from light, humidity, pressure when close to things for more than 10sec
				break;
			}
		}
	};
	
	private class PreProcessThread extends Thread {
		@Override
		public void run() {
			while (IS_RUNNING) {
				try {
					sleep(10000);
					Log.d("SmartphoneSensingService", "Thread run 10sec message");
					
					String currentTime = getCurrentTimeStamp();
					SensorData data = new SensorData(currentTime);
					
					data.setType("LIGHT");
					data.setData(getAverageData(lightValues));
				    sensorDatabase.addSensorData(data);
				    data.setType("ACCELERATION_X");
				    data.setData(getAverageData(accelerationsX));
				    sensorDatabase.addSensorData(data);
				    data.setType("ACCELERATION_Y");
				    data.setData(getAverageData(accelerationsX));
				    sensorDatabase.addSensorData(data);
				    data.setType("ACCELERATION_Z");
				    data.setData(getAverageData(accelerationsX));
				    sensorDatabase.addSensorData(data);
				    data.setType("PRESSURE");
				    data.setData(getAverageData(pressureValues));
				    sensorDatabase.addSensorData(data);
				    data.setType("HUMIDITY");
				    data.setData(getAverageData(relHumidityValues));
				    sensorDatabase.addSensorData(data);
				    data.setType("TEMPERATURE");
				    data.setData(getAverageData(ambTemperatureValues));
				    sensorDatabase.addSensorData(data);
					
				    clearBuffers();
		
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		private Float getAverageData(ArrayList<Float> valuesList) {
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
		    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date now = new Date();
		    String strDate = sdfDate.format(now);
		    return strDate;
		}
	}

}
