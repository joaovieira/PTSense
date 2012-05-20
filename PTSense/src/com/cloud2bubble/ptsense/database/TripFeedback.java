package com.cloud2bubble.ptsense.database;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.cloud2bubble.ptsense.list.ReviewItem;

public class TripFeedback implements Serializable, ServerObject {

	private static final long serialVersionUID = 1L;
	private static final String TYPE = "trip_feedback";

	private ReviewItem trip;
	private Map<String, Double> inputs = new HashMap<String, Double>();
	private String comment;

	public TripFeedback(ReviewItem trip) {
		this.trip = trip;
	}

	public void addInput(String key, Double value) {
		inputs.put(key, value);
	}

	public void addComment(String text) {
		this.comment = text;
	}

	public String getComment() {
		return comment;
	}

	public Map<String, Double> getInputs() {
		return inputs;
	}

	public ReviewItem getTrip() {
		return trip;
	}

	public long getReviewId() {
		return trip.getId();
	}

	public JSONObject toJSON() {
		JSONObject jsonFeedback = new JSONObject();
		try {
			jsonFeedback.put("type", TYPE);
			
			// trip info
			JSONObject jsonTrip = new JSONObject();
			jsonTrip.put("database_id", trip.getId());
			jsonTrip.put("reviewed", trip.isReviewed());
			jsonTrip.put("line", trip.line);
			jsonTrip.put("service", trip.service);
			jsonTrip.put("origin", trip.origin);
			jsonTrip.put("destination", trip.destination);
			
			SimpleDateFormat sdfDate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			
			jsonTrip.put("start_time", 
					sdfDate.format(trip.startTime.getTime()));
			jsonTrip.put("end_time", 
					sdfDate.format(trip.endTime.getTime()));
			jsonFeedback.put("trip_info", jsonTrip);
			
			// inputs
			JSONObject jsonInputs = new JSONObject();
			Iterator<Entry<String, Double>> it = inputs.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Double> pairs = (Map.Entry<String, Double>) it
						.next();
				jsonInputs.put(pairs.getKey(), pairs.getValue().toString());
			}
			jsonFeedback.put("inputs", jsonInputs);
			jsonFeedback.put("comment", comment);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonFeedback;
	}
}
