package com.cloud2bubble.ptsense.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private static final String KEY_TIME = "time";
	private static final String KEY_TYPE = "type";
	private static final String KEY_DATA = "data";

	// Reviews Table Columns names
	// private static final String KEY_ID = "id";
	private static final String KEY_LINE = "line";
	private static final String KEY_SERVICE = "service";
	private static final String KEY_ORIGIN = "origin";
	private static final String KEY_DESTINATION = "destination";
	// private static final String KEY_TIME = "time";
	private static final String KEY_REVIEWED = "reviewed";

	// Feedback Table Columns names
	// private static final String KEY_ID = "id";
	private static final String REVIEW_ID = "review_id";
	private static final String KEY_COMMENT = "comment";
	private static String KEY_HAPPY;
	private static String KEY_RELAXED;
	private static String KEY_NOISY;
	private static String KEY_CROWDED;
	private static String KEY_SMOOTHNESS;
	private static String KEY_AMBIENCE;
	private static String KEY_FAST;
	private static String KEY_RELIABLE;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		KEY_HAPPY = context.getString(R.string.feedback_key_happy);
		KEY_RELAXED = context.getString(R.string.feedback_key_relaxed);
		KEY_NOISY = context.getString(R.string.feedback_key_noisy);
		KEY_CROWDED = context.getString(R.string.feedback_key_crowded);
		KEY_SMOOTHNESS = context.getString(R.string.feedback_key_smoothness);
		KEY_AMBIENCE = context.getString(R.string.feedback_key_ambience);
		KEY_FAST = context.getString(R.string.feedback_key_fast);
		KEY_RELIABLE = context.getString(R.string.feedback_key_reliable);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SENSORDATA_TABLE = "CREATE TABLE " + TABLE_SENSORDATA
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
				+ KEY_TYPE + " TEXT," + KEY_DATA + " REAL)";
		db.execSQL(CREATE_SENSORDATA_TABLE);

		String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_LINE + " TEXT,"
				+ KEY_SERVICE + " TEXT," + KEY_ORIGIN + " TEXT,"
				+ KEY_DESTINATION + " TEXT," + KEY_TIME + " TEXT,"
				+ KEY_REVIEWED + " INTEGER)";
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
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIME, sensorData.time);
		values.put(KEY_TYPE, sensorData.type);
		values.put(KEY_DATA, sensorData.data);

		long rowID = db.insert(TABLE_SENSORDATA, null, values);
		db.close(); // Close database connection
		return rowID;
	}

	// Get all sensor data by type
	public List<SensorData> getSensorDataByType(String type) {
		List<SensorData> sensorDataList = new ArrayList<SensorData>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_SENSORDATA + " WHERE "
				+ KEY_TYPE + " LIKE " + type;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				SensorData contact = new SensorData(cursor.getString(1),
						cursor.getString(2), cursor.getFloat(3));
				sensorDataList.add(contact);
			} while (cursor.moveToNext());
		}

		return sensorDataList;
	}

	// Get all sensor data
	public List<SensorData> getAllSensorData() {
		List<SensorData> sensorDataList = new ArrayList<SensorData>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_SENSORDATA;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				SensorData sensorData = new SensorData(cursor.getString(1),
						cursor.getString(2), cursor.getFloat(3));
				sensorDataList.add(sensorData);
			} while (cursor.moveToNext());
		}

		return sensorDataList;
	}

	// Add new review
	public long addPendingReview(ReviewItem reviewItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LINE, reviewItem.line);
		values.put(KEY_SERVICE, reviewItem.service);
		values.put(KEY_ORIGIN, reviewItem.origin);
		values.put(KEY_DESTINATION, reviewItem.destination);
		values.put(KEY_TIME, dateToString(reviewItem.date));
		int intReviewed = (reviewItem.isReviewed()) ? 1 : 0;
		values.put(KEY_REVIEWED, intReviewed);

		long rowID = db.insert(TABLE_REVIEWS, null, values);
		db.close(); // Close database connection

		return rowID;
	}

	// Add new contact
	public void removePendingReview(ReviewItem reviewItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_REVIEWS, KEY_ID + "=?",
				new String[] { String.valueOf(reviewItem.getId()) });
		db.close(); // Close database connection
	}

	// Get all sensor data
	public ArrayList<ReviewItem> getAllPendingReviews() {
		ArrayList<ReviewItem> reviewsList = new ArrayList<ReviewItem>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_REVIEWS + " WHERE "
				+ KEY_REVIEWED + "=0";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ReviewItem review = new ReviewItem(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						stringToDate(cursor.getString(5)), cursor.getInt(6));
				reviewsList.add(review);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return reviewsList;
	}

	// update review as reviewd
	public int updateReviewAsReviewed(ReviewItem reviewItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LINE, reviewItem.line);
		values.put(KEY_SERVICE, reviewItem.service);
		values.put(KEY_ORIGIN, reviewItem.origin);
		values.put(KEY_DESTINATION, reviewItem.destination);
		values.put(KEY_TIME, dateToString(reviewItem.date));
		values.put(KEY_REVIEWED, 1);

		// updating row
		int ret = db.update(TABLE_REVIEWS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(reviewItem.getId()) });
		db.close();
		return ret;
	}

	// Add new contact
	public long addPendingFeedback(TripFeedback feedback) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(REVIEW_ID, feedback.getReviewId());

		Map<String, Double> inputs = feedback.getInputs();
		Iterator<Entry<String, Double>> it = inputs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Double> pairs = (Map.Entry<String, Double>) it
					.next();
			values.put(pairs.getKey(), pairs.getValue().toString());
		}
		values.put(KEY_COMMENT, feedback.getcomment());

		long rowID = db.insert(TABLE_FEEDBACKS, null, values);
		db.close(); // Close database connection

		return rowID;
	}

	// Add new contact
	public void removePendingFeedback(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FEEDBACKS, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close(); // Close database connection
	}

	// Get all sensor data
	public List<TripFeedback> getAllPendingFeedbacks() {
		List<TripFeedback> feedbackList = new ArrayList<TripFeedback>();
		// Select All Query
		String selectQuery = "SELECT " + TABLE_FEEDBACKS + "." + KEY_ID + ","
				+ KEY_LINE + "," + KEY_SERVICE + "," + KEY_ORIGIN + ","
				+ KEY_DESTINATION + "," + KEY_TIME + "," + KEY_HAPPY + ","
				+ KEY_RELAXED + "," + KEY_NOISY + "," + KEY_CROWDED + ","
				+ KEY_SMOOTHNESS + "," + KEY_AMBIENCE + "," + KEY_FAST + ","
				+ KEY_RELIABLE + "," + KEY_COMMENT

				+ " FROM " + TABLE_FEEDBACKS + "," + TABLE_REVIEWS + " WHERE "
				+ TABLE_FEEDBACKS + "." + REVIEW_ID + "=" + TABLE_REVIEWS + "."
				+ KEY_ID;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ReviewItem trip = new ReviewItem(cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), stringToDate(cursor.getString(5)));
				TripFeedback feedback = new TripFeedback(trip);
				feedback.addInput(KEY_HAPPY, Double.parseDouble(cursor.getString(6)));
				feedback.addInput(KEY_RELAXED, Double.parseDouble(cursor.getString(7)));
				feedback.addInput(KEY_NOISY, Double.parseDouble(cursor.getString(8)));
				feedback.addInput(KEY_CROWDED, Double.parseDouble(cursor.getString(9)));
				feedback.addInput(KEY_SMOOTHNESS, Double.parseDouble(cursor.getString(10)));
				feedback.addInput(KEY_AMBIENCE, Double.parseDouble(cursor.getString(11)));
				feedback.addInput(KEY_FAST, Double.parseDouble(cursor.getString(12)));
				feedback.addInput(KEY_RELIABLE, Double.parseDouble(cursor.getString(12)));
				feedback.addComment(cursor.getString(14));
				feedbackList.add(feedback);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return feedbackList;
	}

	private Calendar stringToDate(String stringDate) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdfDate.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private String dateToString(Calendar date) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdfDate.format(date.getTime());
	}

	// --- remove

	public void clearTables() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSORDATA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACKS);

		onCreate(db);
	}

}