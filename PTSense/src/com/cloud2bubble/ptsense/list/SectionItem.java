package com.cloud2bubble.ptsense.list;

public class SectionItem extends Item {

	private final String title;

	public SectionItem(String key, String title) {
		this.key = key;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return Item.SECTION;
	}
}
