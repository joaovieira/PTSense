package com.cloud2bubble.ptsense;

public class EmptyItem implements Item {
	
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
		return Item.EMPTY;
	}

}
