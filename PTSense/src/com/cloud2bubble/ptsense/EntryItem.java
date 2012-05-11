package com.cloud2bubble.ptsense;

public class EntryItem implements Item {

	public final String title;
	public final String key;
	public String value;
	public final String unit;

	public EntryItem(String key, String title, String unit) {
		this.key = key;
		this.title = title;
		this.unit = unit;
		this.value = "";
	}

	public boolean isSection() {
		return false;
	}

	public String getKey() {
		return key;
	}
	
	public String toString(){
		return value + " " + unit;
	}
}
