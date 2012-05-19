package com.cloud2bubble.ptsense.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud2bubble.ptsense.list.ReviewItem;

public class TripData {
	
	private ReviewItem trip;
	private List<Calendar> timestamps = new ArrayList<Calendar>();
	private Map<String, ArrayList<Float> > data = new HashMap<String, ArrayList<Float> >();

	public TripData(ReviewItem trip, ArrayList<Calendar> timestamps,
			Map<String, ArrayList<Float>> data) {
		this.trip = trip;
		this.timestamps = timestamps;
		this.data = data;
	}
	
	public ReviewItem getTrip(){
		return trip;
	}
	
	public List<Calendar> getTimestamps(){
		return timestamps;
	}
	
	public Map<String, ArrayList<Float> > getData(){
		return data;
	}
}
