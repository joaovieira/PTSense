package com.cloud2bubble.ptsense;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SmartphoneSensingService extends Service implements
		SensorEventListener {

	private static final int ONGOING_NOTIFICATION = 1;
	public static boolean IS_RUNNING;
	private Thread sensorThread = null;
	SensorManager sensorManager = null;
	float x, y, z;

	@Override
	public void onCreate() {
		super.onCreate();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_NORMAL);
		x = y = z = 0;
	}

	private void unregisterSensorListeners() {
		sensorManager.unregisterListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		sensorManager.unregisterListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
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

	private class PreProcessThread extends Thread {
		@Override
		public void run() {
			while (IS_RUNNING) {
				try {
					sleep(10000);
					Log.d("SmartphoneSensingService", "Thread run 10sec message");
					// TODO pre process buffer and store results in database
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				x = event.values[0];
				y = event.values[1];
				z = event.values[2];
				// TODO store variables in buffer
				break;
			case Sensor.TYPE_LIGHT:
				break;

			}
		}
	};

}
