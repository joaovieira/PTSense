package com.cloud2bubble.ptsense.servercommunication;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.Sensing;
import com.cloud2bubble.ptsense.activity.UserFeedback;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class C2BClient extends Service {

	public static final int FEEDBACK_NOTIFICATION = 11;
	public static final int INFERENCE_NOTIFICATION = 12;

	private DatabaseHandler database;
	NotificationManager nManager;

	@Override
	public void onCreate() {
		super.onCreate();
		database = new DatabaseHandler(this);
		nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		//Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		
		if (intent != null) {
			long tripId = intent.getLongExtra("trip_id", -1);
			if (tripId != -1)
				sendTripDataToServer(tripId);
		} else {
			// TODO check for pending feedbacks to send to server
			stopSelf();
		}

		return START_NOT_STICKY;
	}

	private void sendTripDataToServer(long id) {
		// TODO communicate with server for inference
		// now just show a notification asking for user feedback
		ReviewItem trip = database.getReview(id);
		notifyForFeedback(trip);
		stopSelf();
	}

	private void notifyForFeedback(ReviewItem trip) {
		String tickerText = getString(R.string.notification_review_title)
				+ " for " + trip.serviceToString() + " " + trip.directionToString();
		Intent notificationIntent = new Intent(this, UserFeedback.class);
		notificationIntent.putExtra("review_item", trip);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0); // Intent.FLAG_ACTIVITY_NEW_TASK
		Notification notification = new Notification(
				R.drawable.ic_stat_feedback, tickerText,
				System.currentTimeMillis());
		notification.setLatestEventInfo(this,
				getText(R.string.notification_review_title),
				getText(R.string.notification_feedback_message), pendingIntent);
		nManager.notify(FEEDBACK_NOTIFICATION, notification);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		//Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
