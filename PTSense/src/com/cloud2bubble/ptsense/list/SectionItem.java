package com.cloud2bubble.ptsense.list;

public class SectionItem implements SensorDataItem {

	private final String title;
	private final String key;

	public SectionItem(String key, String title) {
		this.key = key;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getKey() {
		return key;
	}

	public int getType() {
		return SensorDataItem.SECTION;
	}
}
