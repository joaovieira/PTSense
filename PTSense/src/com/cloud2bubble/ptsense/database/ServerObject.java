package com.cloud2bubble.ptsense.database;

import org.json.JSONObject;

public interface ServerObject {
	
	public static final int TRIP_DATA = 0;
	public static final int TRIP_FEEDBACK = 1;
	
	public JSONObject toJSON();
	public int getType();
}
