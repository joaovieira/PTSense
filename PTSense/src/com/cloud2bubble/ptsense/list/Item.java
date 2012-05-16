package com.cloud2bubble.ptsense.list;

public abstract class Item {
	
	public static final int SECTION = 1;
	public static final int EMPTY = 2;
	public static final int ENTRY = 3;
	public static final int INPUT = 4;
	public static final int TEXT_INPUT = 5;
	
	protected String key;

	public abstract int getType();
	public String getKey() {
		return key;
	}
}
