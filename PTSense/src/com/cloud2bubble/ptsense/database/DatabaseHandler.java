package com.cloud2bubble.ptsense.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "PTSense_database";

	// Table names
	private static final String TABLE_SENSORDATA = "sensordata";
	private static final String TABLE_REVIEWS = "reviews";
	private static final String TABLE_FEEDBACKS = "feedbacks";

	// SensorData Table Columns names
	private static final String KEY_ID = "id";
	private static final String REVIEW_ID = "review_id";
	private static final String KEY_TIME = "time";
	private static String KEY_ACCELERATION;
	private static String KEY_LIGHT;
	private static String KEY_PRESSURE;
	private static String KEY_PROXIMITY;
	private static String KEY_SOUND;
	private static String KEY_LATITUDE;
	private static String KEY_LONGITUDE;

	// Reviews Table Columns names
	// private static final String KEY_ID = "id";
	private static final String KEY_LINE = "line";
	private static final String KEY_SERVICE = "service";
	private static final String KEY_ORIGIN = "origin";
	private static final String KEY_DESTINATION = "destination";
	private static final String KEY_START_TIME = "start_time";
	private static final String KEY_END_TIME = "end_time";
	private static final String KEY_REVIEWED = "reviewed";

	// Feedback Table Columns names
	// private static final String KEY_ID = "id";
	// private static final String REVIEW_ID = "review_id";
	private static final String KEY_COMMENT = "comment";
	private static String KEY_HAPPY;
	private static String KEY_RELAXED;
	private static String KEY_NOISY;
	private static String KEY_CROWDED;
	private static String KEY_SMOOTHNESS;
	private static String KEY_AMBIENCE;
	private static String KEY_FAST;
	private static String KEY_RELIABLE;

	private static DatabaseHandler instance;

	private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		KEY_ACCELERATION = context
				.getString(R.string.sensordata_key_acceleration);
		KEY_LIGHT = context.getString(R.string.sensordata_key_light);
		KEY_PRESSURE = context.getString(R.string.sensordata_key_pressure);
		KEY_PROXIMITY = context.getString(R.string.sensordata_key_proximity);
		KEY_SOUND = context.getString(R.string.sensordata_key_sound);
		KEY_LATITUDE = context.getString(R.string.sensordata_key_latitude);
		KEY_LONGITUDE = context.getString(R.string.sensordata_key_longitude);

		KEY_HAPPY = context.getString(R.string.feedback_key_happy);
		KEY_RELAXED = context.getString(R.string.feedback_key_relaxed);
		KEY_NOISY = context.getString(R.string.feedback_key_noisy);
		KEY_CROWDED = context.getString(R.string.feedback_key_crowded);
		KEY_SMOOTHNESS = context.getString(R.string.feedback_key_smoothness);
		KEY_AMBIENCE = context.getString(R.string.feedback_key_ambience);
		KEY_FAST = context.getString(R.string.feedback_key_fast);
		KEY_RELIABLE = context.getString(R.string.feedback_key_reliable);

	}

	public static DatabaseHandler getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseHandler(context);
		}
		return instance;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SENSORDATA_TABLE = "CREATE TABLE " + TABLE_SENSORDATA
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + REVIEW_ID
				+ " INTEGER," + KEY_TIME + " TEXT," + KEY_ACCELERATION
				+ " REAL," + KEY_LIGHT + " REAL," + KEY_PRESSURE + " REAL,"
				+ KEY_PROXIMITY + " REAL," + KEY_SOUND + " REAL,"
				+ KEY_LATITUDE + " REAL," + KEY_LONGITUDE + " REAL)";
		db.execSQL(CREATE_SENSORDATA_TABLE);

		String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_LINE + " TEXT,"
				+ KEY_SERVICE + " TEXT," + KEY_ORIGIN + " TEXT,"
				+ KEY_DESTINATION + " TEXT," + KEY_START_TIME + " TEXT,"
				+ KEY_END_TIME + " TEXT," + KEY_REVIEWED + " INTEGER)";
		db.execSQL(CREATE_REVIEWS_TABLE);

		String CREATE_FEEDBACKS_TABLE = "CREATE TABLE " + TABLE_FEEDBACKS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + REVIEW_ID + " INTEGER,"
				+ KEY_HAPPY + " TEXT," + KEY_RELAXED + " TEXT," + KEY_NOISY
				+ " TEXT," + KEY_CROWDED + " TEXT," + KEY_SMOOTHNESS + " TEXT,"
				+ KEY_AMBIENCE + " TEXT," + KEY_FAST + " TEXT," + KEY_RELIABLE
				+ " TEXT," + KEY_COMMENT + " TEXT)";
		db.execSQL(CREATE_FEEDBACKS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older tables if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSORDATA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACKS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Add new sensor data
	public long addSensorData(SensorData sensorData) {
		ContentValues values = new ContentValues();
		values.put(REVIEW_ID, sensorData.refTripId);
		values.put(KEY_TIME, sensorData.time);

		Map<String, Float> data = sensorData.getData();
		Iterator<Entry<String, Float>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Float> pairs = (Map.Entry<String, Float>) it
					.next();
			values.put(pairs.getKey(), pairs.getValue().toString());
		}

		SQLiteDatabase db = this.getWritableDatabase();
		long rowID = db.insert(TABLE_SENSORDATA, null, values);
		db.close(); // Close database connection
		return rowID;
	}

	// Add new contact
	public void removeTripData(TripData tripData) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SENSORDATA, REVIEW_ID + "=?",
				new String[] { String.valueOf(tripData.getTrip().getId()) });
		db.close(); // Close database connection
	}

	// Get trip data
	public TripData getTripData(long id) {
		ReviewItem trip = getReview(id);
		ArrayList<Calendar> timestamps = new ArrayList<Calendar>();
		ArrayList<Float> acceleration = new ArrayList<Float>();
		ArrayList<Float> light = new ArrayList<Float>();
		ArrayList<Float> pressure = new ArrayList<Float>();
		ArrayList<Float> proximity = new ArrayList<Float>();
		ArrayList<Float> sound = new ArrayList<Float>();
		ArrayList<Float> latitude = new ArrayList<Float>();
		ArrayList<Float> longitude = new ArrayList<Float>();

		String selectQuery = "SELECT " + KEY_TIME + "," + KEY_ACCELERATION
				+ "," + KEY_LIGHT + "," + KEY_PRESSURE + "," + KEY_PROXIMITY
				+ "," + KEY_SOUND + "," + KEY_LATITUDE + "," + KEY_LONGITUDE
				+ " FROM " + TABLE_SENSORDATA + " WHERE " + REVIEW_ID + "="
				+ id + " ORDER BY " + KEY_TIME + " ASC";

		synchronized (this) {
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					timestamps.add(stringToDate(cursor.getString(0)));
					acceleration.add(cursor.getFloat(1));
					light.add(cursor.getFloat(2));
					pressure.add(cursor.getFloat(3));
					proximity.add(cursor.getFloat(4));
					sound.add(cursor.getFloat(5));
					latitude.add(cursor.getFloat(6));
					longitude.add(cursor.getFloat(7));
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
		}

		Map<String, ArrayList<Float>> data = new HashMap<String, ArrayList<Float>>();
		data.put(KEY_ACCELERATION, acceleration);
		data.put(KEY_LIGHT, light);
		data.put(KEY_PRESSURE, pressure);
		data.put(KEY_SOUND, sound);
		data.put(KEY_LATITUDE, latitude);
		data.put(KEY_LONGITUDE, longitude);

		TripData tripSensorData = new TripData(trip, timestamps, data);
		return tripSensorData;
	}

	// get all pending trip data
	public List<TripData> getAllTripsData() {
		ArrayList<Long> tripIds = new ArrayList<Long>();
		List<TripData> tripData = new ArrayList<TripData>();

		String selectQuery = "SELECT DISTINCT " + REVIEW_ID + " FROM "
				+ TABLE_SENSORDATA + ";";

		synchronized (this) {
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					tripIds.add(cursor.getLong(0));
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
		}

		Iterator<Long> itr = tripIds.iterator();
		while (itr.hasNext()) {
			tripData.add(getTripData(itr.next()));
		}

		return tripData;
	}

	// Add new review
	public long addPendingReview(ReviewItem reviewItem) {
		ContentValues values = new ContentValues();
		values.put(KEY_LINE, reviewItem.line);
		values.put(KEY_SERVICE, reviewItem.service);
		values.put(KEY_ORIGIN, reviewItem.origin);
		values.put(KEY_DESTINATION, reviewItem.destination);
		values.put(KEY_START_TIME, dateToString(reviewItem.startTime));
		values.put(KEY_END_TIME, dateToString(reviewItem.endTime));
		int intReviewed = (reviewItem.isReviewed()) ? 1 : 0;
		values.put(KEY_REVIEWED, intReviewed);

		SQLiteDatabase db = this.getWritableDatabase();
		long rowID = db.insert(TABLE_REVIEWS, null, values);
		db.close(); // Close database connection

		return rowID;
	}

	// Remove review
	public void removePendingReview(ReviewItem reviewItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_REVIEWS, KEY_ID + "=?",
				new String[] { String.valueOf(reviewItem.getId()) });
		db.close(); // Close database connection
	}

	public ReviewItem getReview(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_REVIEWS + " WHERE "
				+ KEY_ID + "=" + id;
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null)
			cursor.moveToFirst();

		ReviewItem trip;
		synchronized (this) {

			trip = new ReviewItem(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), stringToDate(cursor.getString(5)),
					stringToDate(cursor.getString(6)), cursor.getInt(7));

			cursor.close();
			db.close();
		}
		return trip;
	}

	// Get all sensor data
	public ArrayList<ReviewItem> getAllPendingReviews() {
		ArrayList<ReviewItem> reviewsList = new ArrayList<ReviewItem>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_REVIEWS + " WHERE "
				+ KEY_REVIEWED + "=0" + " AND " + KEY_END_TIME + "<>\"\""
				+ " ORDER BY " + KEY_END_TIME + " DESC";

		synchronized (this) {
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					ReviewItem review = new ReviewItem(cursor.getLong(0),
							cursor.getString(1), cursor.getString(2),
							cursor.getString(3), cursor.getString(4),
							stringToDate(cursor.getString(5)),
							stringToDate(cursor.getString(6)), cursor.getInt(7));
					reviewsList.add(review);
				} while (cursor.moveToNext());
			}

			cursor.close();
			db.close();
		}
		return reviewsList;
	}

	// update review as reviewed
	public int updateReviewAsReviewed(ReviewItem reviewItem) {
		ContentValues values = new ContentValues();
		values.put(KEY_LINE, reviewItem.line);
		values.put(KEY_SERVICE, reviewItem.service);
		values.put(KEY_ORIGIN, reviewItem.origin);
		values.put(KEY_DESTINATION, reviewItem.destination);
		values.put(KEY_START_TIME, dateToString(reviewItem.startTime));
		values.put(KEY_END_TIME, dateToString(reviewItem.endTime));
		values.put(KEY_REVIEWED, 1);

		// updating row
		SQLiteDatabase db = this.getWritableDatabase();
		int ret = db.update(TABLE_REVIEWS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(reviewItem.getId()) });
		db.close();
		return ret;
	}

	// update finish time to review
	public int updatePendingReview(ReviewItem reviewItem) {
		int reviewed = reviewItem.isReviewed() ? 1 : 0;
		ContentValues values = new ContentValues();
		values.put(KEY_LINE, reviewItem.line);
		values.put(KEY_SERVICE, reviewItem.service);
		values.put(KEY_ORIGIN, reviewItem.origin);
		values.put(KEY_DESTINATION, reviewItem.destination);
		values.put(KEY_START_TIME, dateToString(reviewItem.startTime));
		values.put(KEY_END_TIME, dateToString(reviewItem.endTime));
		values.put(KEY_REVIEWED, reviewed);

		// updating row
		SQLiteDatabase db = this.getWritableDatabase();
		int ret = db.update(TABLE_REVIEWS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(reviewItem.getId()) });
		db.close();
		return ret;
	}

	// Add new Feedback
	public long addPendingFeedback(TripFeedback feedback) {
		ContentValues values = new ContentValues();
		values.put(REVIEW_ID, feedback.getReviewId());

		Map<String, Double> inputs = feedback.getInputs();
		Iterator<Entry<String, Double>> it = inputs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Double> pairs = (Map.Entry<String, Double>) it
					.next();
			values.put(pairs.getKey(), pairs.getValue().toString());
		}
		values.put(KEY_COMMENT, feedback.getComment());

		SQLiteDatabase db = this.getWritableDatabase();
		long rowID = db.insert(TABLE_FEEDBACKS, null, values);
		db.close(); // Close database connection

		return rowID;
	}

	// Remove Feedback
	public void removePendingFeedback(TripFeedback feedback) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FEEDBACKS, REVIEW_ID + " = ?",
				new String[] { String.valueOf(feedback.getTrip().getId()) });
		db.close(); // Close database connection
	}

	// Get all Feedbacks
	public List<TripFeedback> getAllPendingFeedbacks() {
		Log.d("DatabaseHandler", "Getting all trip data...");

		List<TripFeedback> feedbackList = new ArrayList<TripFeedback>();
		// Select All Query
		String selectQuery = "SELECT " + TABLE_REVIEWS + "." + KEY_ID + ","
				+ KEY_LINE + "," + KEY_SERVICE + "," + KEY_ORIGIN + ","
				+ KEY_DESTINATION + "," + KEY_START_TIME + "," + KEY_END_TIME
				+ "," + KEY_HAPPY + "," + KEY_RELAXED + "," + KEY_NOISY + ","
				+ KEY_CROWDED + "," + KEY_SMOOTHNESS + "," + KEY_AMBIENCE + ","
				+ KEY_FAST + "," + KEY_RELIABLE + "," + KEY_COMMENT

				+ " FROM " + TABLE_FEEDBACKS + "," + TABLE_REVIEWS + " WHERE "
				+ TABLE_FEEDBACKS + "." + REVIEW_ID + "=" + TABLE_REVIEWS + "."
				+ KEY_ID;

		synchronized (this) {

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					ReviewItem trip = new ReviewItem(cursor.getLong(0),
							cursor.getString(1), cursor.getString(2),
							cursor.getString(3), cursor.getString(4),
							stringToDate(cursor.getString(5)),
							stringToDate(cursor.getString(6)), cursor.getInt(7));
					TripFeedback feedback = new TripFeedback(trip);
					feedback.addInput(KEY_HAPPY,
							Double.parseDouble(cursor.getString(7)));
					feedback.addInput(KEY_RELAXED,
							Double.parseDouble(cursor.getString(8)));
					feedback.addInput(KEY_NOISY,
							Double.parseDouble(cursor.getString(9)));
					feedback.addInput(KEY_CROWDED,
							Double.parseDouble(cursor.getString(10)));
					feedback.addInput(KEY_SMOOTHNESS,
							Double.parseDouble(cursor.getString(11)));
					feedback.addInput(KEY_AMBIENCE,
							Double.parseDouble(cursor.getString(12)));
					feedback.addInput(KEY_FAST,
							Double.parseDouble(cursor.getString(13)));
					feedback.addInput(KEY_RELIABLE,
							Double.parseDouble(cursor.getString(14)));
					feedback.addComment(cursor.getString(15));
					feedbackList.add(feedback);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
		}
		return feedbackList;
	}

	private Calendar stringToDate(String stringDate) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdfDate.parse(stringDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			return null;
		}
	}

	private String dateToString(Calendar date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdfDate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return sdfDate.format(date.getTime());
		}
	}

}