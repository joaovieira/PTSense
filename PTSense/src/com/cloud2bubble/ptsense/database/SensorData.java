package com.cloud2bubble.ptsense.database;

import java.util.HashMap;
import java.util.Map;

public class SensorData {

	long refTripId;
	String time;
	Map<String, Float> data = new HashMap<String, Float>();
	
	public SensorData(long tripId, String time){
		this.refTripId = tripId;
		this.time = time;
	}
	
	public void addData(String key, Float value) {
		data.put(key, value);
	}

	public Map<String, Float> getData() {
		return data;
	}
}
