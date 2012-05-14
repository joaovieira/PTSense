package com.cloud2bubble.ptsense.database;

public class SensorData {
	
	String time, type;
	Float data;
	
	public SensorData(String mTime, String mType, Float mData){
		time = mTime;
		type = mType;
		data = mData;
	}

	public SensorData(String mTime) {
		time = mTime;
		type = null;
		data = null;
	}

	public void setType(String mType){
		type = mType;
	}
	
	public void setData(Float mData){
		data = mData;
	}

}
