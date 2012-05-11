package com.cloud2bubble.ptsense;

public class SectionItem implements Item {

	private final String title;
	private final String key;

	public SectionItem(String key, String title) {
		this.key = key;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public boolean isSection() {
		return true;
	}

	public String getKey() {
		return key;
	}
}
