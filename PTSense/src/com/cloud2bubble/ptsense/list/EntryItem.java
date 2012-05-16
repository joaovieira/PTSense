package com.cloud2bubble.ptsense.list;

public class EntryItem extends Item {

	public final String title;
	public String value;
	public final String unit;

	public EntryItem(String key, String title, String unit) {
		this.key = key;
		this.title = title;
		this.unit = unit;
		this.value = "";
	}
	
	public String toString(){
		return value + " " + unit;
	}

	public int getType() {
		return Item.ENTRY;
	}
}
