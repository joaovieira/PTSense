package com.cloud2bubble.ptsense;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SensorDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "sensorDataManager";

	// Contacts table name
	private static final String TABLE_SENSORDATA = "sensordata";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TIME = "time";
	private static final String KEY_TYPE = "type";
	private static final String KEY_DATA = "data";

	public SensorDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SENSORDATA_TABLE = "CREATE TABLE " + TABLE_SENSORDATA
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
				+ KEY_TYPE + " TEXT," + KEY_DATA + " REAL" + ")";
		db.execSQL(CREATE_SENSORDATA_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSORDATA);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Add new contact
	void addSensorData(SensorData sensorData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIME, sensorData.time);
		values.put(KEY_TYPE, sensorData.type);
		values.put(KEY_DATA, sensorData.data);

		db.insert(TABLE_SENSORDATA, null, values);
		db.close(); // Close database connection
	}

	// Get all sensor data by type
	List<SensorData> getSensorDataByType(String type) {
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
				SensorData contact = new SensorData(cursor.getString(1),
						cursor.getString(2), cursor.getFloat(3));
				sensorDataList.add(contact);
			} while (cursor.moveToNext());
		}

		return sensorDataList;
	}

}