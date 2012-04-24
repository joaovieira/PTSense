package com.cloud2bubble.ptsense;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SmartphoneSensingService extends Service {

	private static final int ONGOING_NOTIFICATION = 1;
	public static boolean IS_RUNNING; 
	
	/**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class SmartphoneSensingBinder extends Binder {
    	SmartphoneSensingService getService() {
            return SmartphoneSensingService.this;
        }
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SmartphoneSensingService", "Received start id " + startId + ": " + intent);
        
        IS_RUNNING = true;
        startOnGoingNotification();
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private void startOnGoingNotification() {
    	// TODO later update: use Notification.Builder after Api 11...
    	Notification notification = new Notification(R.drawable.ic_stat_sensing, 
    			getText(R.string.notification_sensing_ticker_text),
    	        System.currentTimeMillis());
    	Intent notificationIntent = new Intent(this, Sensing.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    	notification.setLatestEventInfo(this, getText(R.string.notification_sensing_title),
    	        getText(R.string.notification_sensing_message), pendingIntent);
    	startForeground(ONGOING_NOTIFICATION, notification);
	}
	
	private void collectDataFromSensors() {	
    	// TODO collect sensor data in new thread
	}
    
	private void stop() {
		stopForeground(true);

        // Tell the user we stopped.
        Toast.makeText(this, "Stopped sensing", Toast.LENGTH_SHORT).show();
	}

}
