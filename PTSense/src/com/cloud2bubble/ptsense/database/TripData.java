package com.cloud2bubble.ptsense.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.cloud2bubble.ptsense.list.ReviewItem;

public class TripData implements ServerObject {

	private ReviewItem trip;
	private List<Calendar> timestamps = new ArrayList<Calendar>();
	private Map<String, ArrayList<Float>> data = new HashMap<String, ArrayList<Float>>();

	public TripData(ReviewItem trip, ArrayList<Calendar> timestamps,
			Map<String, ArrayList<Float>> data) {
		this.trip = trip;
		this.timestamps = timestamps;
		this.data = data;
	}

	public ReviewItem getTrip() {
		return trip;
	}

	public List<Calendar> getTimestamps() {
		return timestamps;
	}

	public Map<String, ArrayList<Float>> getData() {
		return data;
	}

	public JSONObject toJSON() {
		JSONObject jsonTripData = new JSONObject();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (trip != null) {
				// trip info
				Log.d("TripData",
						"getting json for tripdata for trip:" + trip.toString());

				JSONObject jsonTrip = new JSONObject();
				jsonTrip.put("database_id", trip.getId());
				jsonTrip.put("reviewed", trip.isReviewed());
				jsonTrip.put("line", trip.line);
				jsonTrip.put("service", trip.service);
				jsonTrip.put("origin", trip.origin);
				jsonTrip.put("destination", trip.destination);

				if (trip.startTime != null)
					jsonTrip.put("start_time",
							sdfDate.format(trip.startTime.getTime()));
				else
					jsonTrip.put("start_time", "ERROR");

				if (trip.endTime != null)
					jsonTrip.put("end_time",
							sdfDate.format(trip.endTime.getTime()));
				else
					jsonTrip.put("end_time", "ERROR");

				jsonTripData.put("trip_info", jsonTrip);
			}
			
			// sensor data
			JSONObject jsonSensorData = new JSONObject();

			// timestamps
			JSONArray jsonTimestamps = new JSONArray();
			for (Calendar t : timestamps) {
				if (t != null)
					jsonTimestamps.put(sdfDate.format(t.getTime()));
				else
					jsonTimestamps.put("ERROR");
			}
			jsonSensorData.put("timestamps", jsonTimestamps);

			Iterator<Entry<String, ArrayList<Float>>> it = data.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, ArrayList<Float>> pairs = (Map.Entry<String, ArrayList<Float>>) it
						.next();
				// populate array with sensor values
				JSONArray jsonValues = new JSONArray();
				for (Float f : pairs.getValue()) {
					if (f == null)
						jsonValues.put("ERROR");
					else if (f == Float.NEGATIVE_INFINITY)
						jsonValues.put(JSONObject.NULL);
					else if (f.isNaN())
						jsonValues.put(JSONObject.NULL);
					else
						jsonValues.put(f);
				}
				jsonSensorData.put(pairs.getKey(), jsonValues);
			}
			jsonTripData.put("sensor_data", jsonSensorData);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonTripData;
	}

	public int getType() {
		return ServerObject.TRIP_DATA;
	}

}
