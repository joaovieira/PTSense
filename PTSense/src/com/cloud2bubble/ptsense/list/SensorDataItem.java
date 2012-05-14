package com.cloud2bubble.ptsense.list;

public interface SensorDataItem {
	
	static final int SECTION = 1;
	static final int EMPTY = 2;
	static final int ENTRY = 3;

	public int getType();
	public String getKey();
}
