package com.cloud2bubble.ptsense.list;

public class EmptyItem extends Item {
	
	private String text;
	
	public EmptyItem(String key, String text){
		this.key = key;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public int getType() {
		return Item.EMPTY;
	}

}
