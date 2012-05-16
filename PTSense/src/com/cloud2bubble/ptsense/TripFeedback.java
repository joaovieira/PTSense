package com.cloud2bubble.ptsense;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cloud2bubble.ptsense.list.ReviewItem;

public class TripFeedback implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	ReviewItem trip;
	Map<String, Double> inputs = new HashMap<String, Double>();
	String comment;
	
	public TripFeedback(ReviewItem trip){
		this.trip = trip;
	}

	public void addInput(String key, double d) {
		inputs.put(key, d);
	}

	public void addComment(String text) {
		this.comment = text;
	}
	
	public String getcomment(){
		return comment;
	}
	
}
