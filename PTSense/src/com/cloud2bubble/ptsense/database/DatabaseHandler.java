package com.cloud2bubble.ptsense.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	private static final String DATABASE_NAME = "sensorDataManager";

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

	// Feedback Table Columns names
	// private static final String KEY_ID = "id";
	private static final String REVIEW_ID = "review_id";

	// private static final String KEY_TYPE = "type";
	// private static final String KEY_DATA = "data";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
				+ KEY_DESTINATION + " TEXT," + KEY_TIME + " TEXT)";
		db.execSQL(CREATE_REVIEWS_TABLE);

		String CREATE_FEEDBACKS_TABLE = "CREATE TABLE " + TABLE_FEEDBACKS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + REVIEW_ID + " INTEGER,"
				+ KEY_TYPE + " TEXT," + KEY_DATA + " REAL)";
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

	// Add new contact
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

	// Add new contact
	public long addReview(ReviewItem reviewItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LINE, reviewItem.line);
		values.put(KEY_SERVICE, reviewItem.service);
		values.put(KEY_ORIGIN, reviewItem.origin);
		values.put(KEY_DESTINATION, reviewItem.destination);
		values.put(KEY_TIME, dateToString(reviewItem.date));

		long rowID = db.insert(TABLE_REVIEWS, null, values);
		db.close(); // Close database connection

		return rowID;
	}

	// Add new contact
	public void removeReview(ReviewItem reviewItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_REVIEWS, KEY_ID + " = ?", new String[] { String.valueOf(reviewItem.getId()) });
		db.close(); // Close database connection
	}

	// Get all sensor data
	public ArrayList<ReviewItem> getAllReviews() {
		ArrayList<ReviewItem> reviewsList = new ArrayList<ReviewItem>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_REVIEWS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ReviewItem review = new ReviewItem(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						stringToDate(cursor.getString(5)));
				reviewsList.add(review);
			} while (cursor.moveToNext());
		}

		return reviewsList;
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
		/*
		 * db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSORDATA);
		 * db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
		 * db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACKS);
		 */
		onCreate(db);
	}

}