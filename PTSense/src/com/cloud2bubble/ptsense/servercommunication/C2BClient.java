package com.cloud2bubble.ptsense.servercommunication;

import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.cloud2bubble.ptsense.PTSense;
import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.Home;
import com.cloud2bubble.ptsense.activity.Settings;
import com.cloud2bubble.ptsense.activity.TripReviews;
import com.cloud2bubble.ptsense.activity.UserFeedback;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.database.ServerObject;
import com.cloud2bubble.ptsense.database.TripData;
import com.cloud2bubble.ptsense.database.TripFeedback;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class C2BClient extends IntentService {

	private static final String C2B_DATA_URL = "http://cloud2bubble.appspot.com/api/trip/data";
	private static final String C2B_FEEDBACK_URL = "http://cloud2bubble.appspot.com/api/trip/feedback";

	private boolean COMMUNICATION_RUNNING;
	JSONObject responseJSON;

	public C2BClient() {
		super("C2BClient");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent.hasExtra("trip_id")) {
			long tripId = intent.getLongExtra("trip_id", -1);
			if (tripId != -1) {
				Log.d("C2BClient", "Sending trip data with id:" + tripId);
				sendTripData(tripId);
				stopSelf();
			}
		} else {
			Log.d("C2BClient", "Sending pending data");
			sendPendingData();
			stopSelf();
		}
	}

	private void sendPendingData() {
		PTSense app = (PTSense) getApplication();
		DatabaseHandler database = app.getDatabase();

		// send trip data to server and wait for inference
		if (hasDataConnection()) {
			List<TripFeedback> pendingsFeedbacks = database
					.getAllPendingFeedbacks();

			boolean done = true;
			List<TripData> pendingTripData = database.getAllTripsData();
			Iterator<TripData> itr2 = pendingTripData.iterator();
			while (itr2.hasNext()) {
				TripData tripData = itr2.next();
				Log.d("C2bClient", "Sending tripdata for trip:" + tripData.getTrip().toString());
				if (app.isSensing()
						&& tripData.getTrip().getId() == app.getCurrentTrip()
								.getId()) {
					continue;
				} else {
					if (!sendDataToServer(tripData)) {
						done = false;
						continue;
					} else {
						Log.d("C2bClient", "Removing tripdata from trip with id:" + tripData.getTrip().getId());
						database.removeTripData(tripData);
					}
				}
			}

			Iterator<TripFeedback> itr = pendingsFeedbacks.iterator();
			while (itr.hasNext()) {
				TripFeedback feedback = itr.next();
				Log.d("C2bClient", "Sending feedback for trip:" + feedback.getTrip().toString());
				if (!sendDataToServer(feedback)) {
					done = false;
					continue;
				} else {
					Log.d("C2bClient", "Removing feedback and review from trip with id:" + feedback.getTrip().getId());
					//database.removePendingFeedback(feedback);
					//database.removePendingReview(feedback.getTrip());
				}
			}

			if (done) {
				ignoreInternetConnections();
			} else {
				Log.d("C2BClient", "error while transmitting data to server");
				listenInternetConnections();
			}

		} else {
			Log.d("C2BClient", "no network connections available");
			listenInternetConnections();
		}
	}

	private void sendTripData(long id) {
		PTSense app = (PTSense) getApplication();
		DatabaseHandler database = app.getDatabase();

		// send trip data to server and wait for inference
		if (hasDataConnection()) {
			TripData tripData = database.getTripData(id);
			if (sendDataToServer(tripData)) {
				Log.d("C2bClient", "Removing trip data from trip with id:" + tripData.getTrip().getId());
				//database.removeTripData(tripData);
				ignoreInternetConnections();
			} else {
				// if could not transmit information start listening for
				// internet connections to transmite later
				Log.d("C2BClient", "error while transmitting data to server");
				listenInternetConnections();
			}
		} else {
			Log.d("C2BClient", "no network connections available");
			listenInternetConnections();
		}

		Log.d("C2bClient", "Getting ReviewItem for feedback notification for trip with id:" + id);
		// now just show a notification asking for user feedback
		ReviewItem trip = database.getReview(id);

		SharedPreferences prefs = Settings.getPrefs(this);
		String shouldNotifiy = prefs.getString("notifications", "fail");

		if (!shouldNotifiy.equals("2"))
			notifyForFeedback(trip);
	}

	private void listenInternetConnections() {
		Log.d("C2BClient", "LISTENING internet connections");
		ComponentName netWatcher = new ComponentName(this, NetWatcher.class);
		this.getPackageManager().setComponentEnabledSetting(netWatcher,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	private void ignoreInternetConnections() {
		Log.d("C2BClient", "IGNORING internet connections");
		ComponentName netWatcher = new ComponentName(this, NetWatcher.class);
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
		PTSense app = (PTSense) getApplication();
		String tickerText = getString(R.string.notification_review_title)
				+ " for " + trip.serviceToString() + " "
				+ trip.directionToString();

		int feedbackCount = app
				.incrementNotificationCount(PTSense.FEEDBACK_NOTIFICATION);
		/*PendingIntent contentIntent = PendingIntent.getActivities(this,
				PTSense.FEEDBACK_NOTIFICATION,
				makeMessageIntentStack(this, trip, feedbackCount),
				PendingIntent.FLAG_CANCEL_CURRENT);*/
		Resources res = this.getResources();
		NotificationCompat.Builder feedbackBuilder = new NotificationCompat.Builder(
				this);
		feedbackBuilder
				.setSmallIcon(R.drawable.ic_stat_feedback)
				.setAutoCancel(true)
				.setContentTitle(
						res.getString(R.string.notification_review_title))
				.setContentText(
						res.getString(R.string.notification_feedback_message))
				.setDefaults(
						Notification.DEFAULT_LIGHTS
								| Notification.DEFAULT_SOUND)
				.setTicker(tickerText)
				.setWhen(System.currentTimeMillis())
				.setContentIntent(makeMessageIntentStack(this, trip, feedbackCount));
		;

		if (feedbackCount > 1) {
			feedbackBuilder.setNumber(feedbackCount);
		}

		Notification feedbackNotification = feedbackBuilder.getNotification();

		NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(PTSense.FEEDBACK_NOTIFICATION, feedbackNotification);
	}

	/**
	 * This method creates an array of Intent objects representing the activity
	 * stack for the incoming message details state that the application should
	 * be in when launching it from a notification.
	 */
	static PendingIntent makeMessageIntentStack(Context context,
			ReviewItem trip, int notificationsCount) {
		// A typical convention for notifications is to launch the user deeply
		// into an application representing the data in the notification; to
		// accomplish this, we can build an array of intents to insert the back
		// stack stack history above the item being displayed.
		//int numIntents = (notificationsCount == 1) ? 3 : 2;
		//Intent[] intents = new Intent[numIntents];

		// First: root activity.
		// This is a convenient way to make the proper Intent to launch and
		// reset an application's task.
		/*
		 * intents[0] = Intent.makeRestartActivityTask(new
		 * ComponentName(context, Home.class));
		 */
		TaskStackBuilder intentStack = TaskStackBuilder.from(context)
				.addParentStack(Home.class);

		// "Trip Reviews"
		/*
		 * intents[1] = new Intent(context, TripReviews.class);
		 * intents[1].putExtra("tab", 1);
		 */
		intentStack.addNextIntent(new Intent(context, TripReviews.class)
				.putExtra("tab", 1));

		if (notificationsCount == 1) {
			// Now the activity to display to the user. Also fill in the data it
			// should display.
			/*intents[2] = new Intent(context, UserFeedback.class);
			intents[2].putExtra("review_item", trip);*/
			intentStack.addNextIntent(new Intent(context, UserFeedback.class)
			.putExtra("review_item", trip));
		}
		return intentStack.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
	}

	/**
	 * ******* Communication with server *******
	 */

	private boolean sendDataToServer(ServerObject object) {
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);

		JSONObject sendJSON = object.toJSON();
		try {
			sendJSON.put("device_id", tm.getDeviceId());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String json = sendJSON.toString();
		String postURL;
		if (object.getType() == ServerObject.TRIP_DATA) {
			postURL = C2B_DATA_URL;
		} else {
			postURL = C2B_FEEDBACK_URL;
		}

		COMMUNICATION_RUNNING = true;
		Thread communication = new CommunicationThread(postURL, json);
		communication.start();

		while (COMMUNICATION_RUNNING) {
		}

		if (responseJSON != null) {
			return responseJSON.opt("result").equals("ok");
		} else {
			return false;
		}
	}

	private class CommunicationThread extends Thread {

		String url, json;

		public CommunicationThread(String url, String json) {
			this.url = url;
			this.json = json;
		}

		@Override
		public void run() {
			HttpClient httpClient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/json");

			try {
				StringEntity se = new StringEntity(json, "UTF-8");
				se.setContentType("application/json");
				httpPost.setEntity(se);

				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				responseJSON = new JSONObject(EntityUtils.toString(entity));

				// TODO process response
				Log.d("C2BClient", "sent data to server: JSON=" + json
						+ " RESULT=" + responseJSON.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			COMMUNICATION_RUNNING = false;
		}
	}

}
