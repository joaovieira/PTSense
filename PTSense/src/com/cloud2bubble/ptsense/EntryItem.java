package com.cloud2bubble.ptsense;

public class EntryItem implements Item {

	public final String title;
	public String value;
	public final String key;

	public EntryItem(String key, String title, String value) {
		this.key = key;
		this.title = title;
		this.value = value;
	}

	public boolean isSection() {
		return false;
	}

	public String getKey() {
		return key;
	}

}
