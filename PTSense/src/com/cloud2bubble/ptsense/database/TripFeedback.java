package com.cloud2bubble.ptsense.database;

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

	public void addInput(String key, Double value) {
		inputs.put(key, value);
	}

	public void addComment(String text) {
		this.comment = text;
	}
	
	public String getcomment(){
		return comment;
	}
	
	public Map<String, Double> getInputs(){
		return inputs;
	}
	
	public ReviewItem getTrip(){
		return trip;
	}

	public int getReviewId() {
		return trip.getId();
	}
}
