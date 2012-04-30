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
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SmartphoneSensingService extends Service {

	private static final int ONGOING_NOTIFICATION = 1;
	public static boolean IS_RUNNING;
	private Thread sensorThread = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IS_RUNNING = true;
		startOnGoingNotification();
		collectDataFromSensors();

		/*
		 * SensorManager sensorManager = (SensorManager)
		 * getSystemService(Context.SENSOR_SERVICE); List<Sensor> listSensor =
		 * sensorManager.getSensorList(Sensor.TYPE_ALL);
		 * 
		 * List<String> listSensorType = new ArrayList<String>(); for (int i =
		 * 0; i < listSensor.size(); i++) {
		 * listSensorType.add(listSensor.get(i).getName()); }
		 * 
		 * setListAdapter(new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, listSensorType));
		 * getListView().setTextFilterEnabled(true);
		 */

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

	private void collectDataFromSensors() {
		sensorThread = new SensorThread();
		sensorThread.start();
	}

	private void stop() {
		stopForeground(true);

		// Tell the user we stopped.
		Toast.makeText(this, "Sensing stopped", Toast.LENGTH_SHORT).show();
	}

	private class SensorThread extends Thread {
		@Override
		public void run() {
			while (IS_RUNNING) {
				try {
					sleep(2000);
					Log.d("SmartphoneSensingService", "Thread run 2sec message");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

}
