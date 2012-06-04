package com.cloud2bubble.ptsense.list;

import java.io.Serializable;
import java.util.Calendar;

public class ReviewItem implements Serializable {

	private static final long serialVersionUID = 1L;
	static final int SYSTEM_REVIEW = 1;
	static final int USER_FEEDBACK = 2;

	private long databaseID;
	private boolean reviewed = false;
	public String line, service, origin, destination;
	public Calendar startTime = null;
	public Calendar endTime = null;

	public ReviewItem(long l, String line, String service, String origin,
			String destination, Calendar startTime, Calendar endTime, int reviewed) {
		this.databaseID = l;
		this.line = line;
		this.service = service;
		this.origin = origin;
		this.destination = destination;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reviewed = (reviewed == 1) ? true : false;
	}

	public ReviewItem(String line, String service, String origin,
			String destination, Calendar startTime, Calendar endTime) {
		this.databaseID = -1;
		this.line = line;
		this.service = service;
		this.origin = origin;
		this.destination = destination;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getType() {
		return USER_FEEDBACK;
	}

	public String serviceToString() {
		return line + " (" + service + ")";
	}

	public String directionToString() {
		return origin + " > " + destination;
	}

	public String endTimeToString() {
		String stringTime = formatMonth(endTime.get(Calendar.MONTH)) + " "
				+ endTime.get(Calendar.DAY_OF_MONTH) + "\n";
		stringTime += formatTime(endTime.get(Calendar.HOUR),
				endTime.get(Calendar.MINUTE), endTime.get(Calendar.AM_PM));
		return stringTime;
	}

	private String formatTime(int hour, int minute, int am_pm) {
		hour = hour == 0 ? 12 : hour;
		String time = hour + ":" + minute;
		if (am_pm == Calendar.AM)
			time += "AM";
		else
			time += "PM";
		return time;
	}

	private String formatMonth(int month) {
		switch (month) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";
		default:
			return null;
		}
	}

	public void setDatabaseId(long tripId) {
		databaseID = tripId;
	}
	
	public long getId() {
		return this.databaseID;
	}

	public boolean isReviewed() {
		return this.reviewed;
	}

	public void setReviewed() {
		this.reviewed = true;
	}
	
	public void setEndTime(Calendar endTime){
		this.endTime = endTime;
	}
}
