package com.cloud2bubble.ptsense;

import java.util.GregorianCalendar;

import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.Application;

public class PTSense extends Application {

	public static final int STATE_STOPPED = 0;
	public static final int STATE_SENSING = 1;

	public static final int DIALOG_START_SENSING = 10;
	public static final int DIALOG_STOP_SENSING = 11;
	
	public static final int ONGOING_NOTIFICATION = 100;
	public static final int FEEDBACK_NOTIFICATION = 101;
	public static final int INFERENCE_NOTIFICATION = 102;
	
	private int feedbackCount = 0;
	private int inferenceCount = 0;

	private int state;
	private ReviewItem currentTrip;

	private DatabaseHandler database;


	@Override
	public void onCreate() {
		super.onCreate();
		state = STATE_STOPPED;
		database = DatabaseHandler.getInstance(this);
	}


	/**
	 * ACCESS GLOBAL VARIABLE METHODS
	 */
	public boolean isSensing() {
		return this.state == STATE_SENSING;
	}

	public int getState() {
		return this.state;
	}
	
	public void setState(int newState) {
		this.state = newState;
	}

	public ReviewItem getCurrentTrip() {
		return currentTrip;
	}

	public void createTrip(String service, String line, String origin,
			String destination) {
		ReviewItem newTrip = new ReviewItem(line, service, origin, destination,
				new GregorianCalendar(), null);

		long tripId = database.addPendingReview(newTrip);
		newTrip.setDatabaseId(tripId);
		this.currentTrip = newTrip;
	}

	public long getCurrentTripId() {
		return currentTrip.getId();
	}

	public void updateTrip(String service, String line, String origin,
			String destination) {
		currentTrip.service = service;
		currentTrip.line = line;
		currentTrip.origin = origin;
		currentTrip.destination = destination;
		updateTripInDatabase();
	}
	
	public void updateTripEndTime(GregorianCalendar time) {
		currentTrip.setEndTime(time);
		updateTripInDatabase();
	}

	private void updateTripInDatabase() {
		database.updatePendingReview(currentTrip);
	}

	public boolean isTripInfoCompleted() {
		return !currentTrip.service.isEmpty() && !currentTrip.line.isEmpty()
				&& !currentTrip.origin.isEmpty()
				&& !currentTrip.destination.isEmpty();
	}
	
	public DatabaseHandler getDatabase(){
		return this.database;
	}
	
	public int incrementNotificationCount(int type){
		switch (type){
		case FEEDBACK_NOTIFICATION:
			return ++feedbackCount;
		case INFERENCE_NOTIFICATION:
			return 0;
		}
		return 0;
	}

	public void resetNotificationCount(int type) {
		switch (type){
		case FEEDBACK_NOTIFICATION:
			feedbackCount = 0;
			break;
		case INFERENCE_NOTIFICATION:
			inferenceCount = 0;
			break;
		}
	}

}
