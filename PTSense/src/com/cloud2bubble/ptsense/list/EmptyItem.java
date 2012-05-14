package com.cloud2bubble.ptsense.list;

public class EmptyItem implements SensorDataItem {
	
	private String key;
	private String text;
	
	public EmptyItem(String key, String text){
		this.key = key;
		this.text = text;
	}

	public String getKey() {
		return key;
	}
	
	public String getText() {
		return text;
	}

	public int getType() {
		return SensorDataItem.EMPTY;
	}

}
