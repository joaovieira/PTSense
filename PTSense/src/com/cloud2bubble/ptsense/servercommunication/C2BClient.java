package com.cloud2bubble.ptsense.servercommunication;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.Home;
import com.cloud2bubble.ptsense.activity.TripReviews;
import com.cloud2bubble.ptsense.activity.UserFeedback;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.widget.Toast;

public class C2BClient extends Service {

	public static final int FEEDBACK_NOTIFICATION = 11;
	public static final int INFERENCE_NOTIFICATION = 12;

	NotificationManager nManager;

	public static int feedbackCount = 0;

	private DatabaseHandler database;

	@Override
	public void onCreate() {
		super.onCreate();
		database = new DatabaseHandler(this);
		nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		// Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

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
				+ " for " + trip.serviceToString() + " "
				+ trip.directionToString();

		feedbackCount++;
		PendingIntent contentIntent = PendingIntent.getActivities(this, FEEDBACK_NOTIFICATION,
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

		// Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
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
