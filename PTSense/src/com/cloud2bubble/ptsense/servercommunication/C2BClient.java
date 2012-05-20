package com.cloud2bubble.ptsense.servercommunication;

import java.util.Iterator;
import java.util.List;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.Home;
import com.cloud2bubble.ptsense.activity.TripReviews;
import com.cloud2bubble.ptsense.activity.UserFeedback;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.database.TripData;
import com.cloud2bubble.ptsense.database.TripFeedback;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class C2BClient extends Service {

	public static final int FEEDBACK_NOTIFICATION = 11;
	public static final int INFERENCE_NOTIFICATION = 12;

	NotificationManager nManager;

	public static int feedbackCount = 0;

	private DatabaseHandler database;
	ComponentName netWatcher;

	@Override
	public void onCreate() {
		super.onCreate();
		database = DatabaseHandler.getInstance(this);
		nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		netWatcher = new ComponentName(this, NetWatcher.class);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		if (intent.hasExtra("trip_id")) {
			long tripId = intent.getLongExtra("trip_id", -1);
			if (tripId != -1) {
				sendTripData(tripId);
				stopSelf();
			}
		} else {
			sendPendingData();
			stopSelf();
		}

		return START_NOT_STICKY;
	}

	private void sendPendingData() {
		// send trip data to server and wait for inference
		if (hasDataConnection()) {
			List<TripFeedback> pendingsFeedbacks = database.getAllPendingFeedbacks();
			
			boolean done = true;
			Iterator<TripFeedback> itr = pendingsFeedbacks.iterator();
		    while (itr.hasNext()) {
		    	TripFeedback feedback = itr.next();
		    	if(!sendFeedbackToServer(feedback)){
		    		done = false;
		    		break;
		    	}
		    }
		    
		    List<TripData> pendingTripData = database.getAllTripData();
		    Iterator<TripData> itr2 = pendingTripData.iterator();
		    while (itr2.hasNext()) {
		    	TripData tripData = itr2.next();
		    	if(!sendTripDataToServer(tripData)){
		    		done = false;
		    		break;
		    	}
		    }
		    
		    if(done){
		    	ignoreInternetConnections();
		    }else{
				Log.d("C2BClient", "error while transmitting data to server");
		    	listenInternetConnections();
		    }
		    
		}else{
			Log.d("C2BClient", "no network connections available");
			listenInternetConnections();
		}
	}

	private void sendTripData(long id) {
		// send trip data to server and wait for inference
		if (hasDataConnection()) {
			TripData tripData = database.getTripData(id);
			if (sendTripDataToServer(tripData)) {
				ignoreInternetConnections();
			}else{
				// if could not transmit information start listening for
				// internet connections to transmite later
				Log.d("C2BClient", "error while transmitting data to server");
				listenInternetConnections();
			}
		}else{
			Log.d("C2BClient", "no network connections available");
			listenInternetConnections();
		}

		// now just show a notification asking for user feedback
		ReviewItem trip = database.getReview(id);
		notifyForFeedback(trip);
	}

	private boolean sendTripDataToServer(TripData tripData) {
		// TODO communicate with server - thread
		boolean success = true;
		Log.d("C2BClient", "sent tripdata to server = " + success);
		return success;
	}
	
	private boolean sendFeedbackToServer(TripFeedback feedback) {
		// TODO Auto-generated method stub - thread
		boolean success = true;
		Log.d("C2BClient", "sent feedback to server = " + success);
		return success;
	}

	private void listenInternetConnections() {
		Log.d("C2BClient", "LISTENING internet connections");
		this.getPackageManager().setComponentEnabledSetting(netWatcher,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	private void ignoreInternetConnections() {
		Log.d("C2BClient", "IGNORING internet connections");
		this.getPackageManager().setComponentEnabledSetting(netWatcher,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}

	private boolean hasDataConnection() {
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		} else
			return false;
	}

	private void notifyForFeedback(ReviewItem trip) {
		String tickerText = getString(R.string.notification_review_title)
				+ " for " + trip.serviceToString() + " "
				+ trip.directionToString();

		feedbackCount++;
		PendingIntent contentIntent = PendingIntent.getActivities(this,
				FEEDBACK_NOTIFICATION,
				makeMessageIntentStack(this, trip, feedbackCount),
				PendingIntent.FLAG_CANCEL_CURRENT);
		Resources res = this.getResources();
		Notification.Builder feedbackBuilder = new Notification.Builder(this);
		feedbackBuilder
				.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.ic_stat_feedback)
				.setAutoCancel(true)
				.setContentTitle(
						res.getString(R.string.notification_review_title))
				.setContentText(
						res.getString(R.string.notification_feedback_message))
				.setDefaults(
						Notification.DEFAULT_LIGHTS
								| Notification.DEFAULT_SOUND)
				.setTicker(tickerText).setWhen(System.currentTimeMillis());

		if (feedbackCount > 1) {
			feedbackBuilder.setNumber(feedbackCount);
		}

		Notification feedbackNotification = feedbackBuilder.getNotification();
		nManager.notify(FEEDBACK_NOTIFICATION, feedbackNotification);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * This method creates an array of Intent objects representing the activity
	 * stack for the incoming message details state that the application should
	 * be in when launching it from a notification.
	 */
	static Intent[] makeMessageIntentStack(Context context, ReviewItem trip,
			int notificationsCount) {
		// A typical convention for notifications is to launch the user deeply
		// into an application representing the data in the notification; to
		// accomplish this, we can build an array of intents to insert the back
		// stack stack history above the item being displayed.
		int numIntents = (notificationsCount == 1) ? 3 : 2;
		Intent[] intents = new Intent[numIntents];

		// First: root activity.
		// This is a convenient way to make the proper Intent to launch and
		// reset an application's task.
		intents[0] = Intent.makeRestartActivityTask(new ComponentName(context,
				Home.class));

		// "Trip Reviews"
		intents[1] = new Intent(context, TripReviews.class);
		intents[1].putExtra("tab", 1);
		intents[1].putExtra("clear_fcount", true);

		if (notificationsCount == 1) {
			// Now the activity to display to the user. Also fill in the data it
			// should display.
			intents[2] = new Intent(context, UserFeedback.class);
			intents[2].putExtra("review_item", trip);
			intents[2].putExtra("clear_fcount", true);
		}
		return intents;
	}
}
