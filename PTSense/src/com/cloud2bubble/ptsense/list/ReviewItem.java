package com.cloud2bubble.ptsense.list;

import java.io.Serializable;
import java.util.Calendar;

public class ReviewItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	static final int SYSTEM_REVIEW = 1;
	static final int USER_FEEDBACK = 2;
	
	private final int databaseID;
	public final String line;
	public final String service;
	public final String origin;
	public final String destination;
	public final Calendar date;
	
	public ReviewItem(int id, String line, String service, String origin, String destination, Calendar date){
		this.databaseID = id;
		this.line = line;
		this.service = service;
		this.origin = origin;
		this.destination = destination;
		this.date = date;
	}
	
	public ReviewItem(String line, String service, String origin, String destination, Calendar date){
		this.databaseID = -1;
		this.line = line;
		this.service = service;
		this.origin = origin;
		this.destination = destination;
		this.date = date;
	}

	public int getType(){
		return USER_FEEDBACK;
	}
	
	public String serviceToString(){
		return line + " (" + service + ")";
	}
	
	public String directionToString(){
		return origin + " > " + destination;
	}
	
	public String dateToString(){
		String stringDate =  formatMonth(date.get(Calendar.MONTH)) + " " + date.get(Calendar.DAY_OF_MONTH) + "\n";
		stringDate += formatTime(date.get(Calendar.HOUR), date.get(Calendar.MINUTE), date.get(Calendar.AM_PM));
		return stringDate;
	}

	private String formatTime(int hour, int minute, int am_pm) {
		String time = hour + ":" + minute;
		if(am_pm == Calendar.AM)
			time += "AM";
		else
			time += "PM";
		return time;
	}

	private String formatMonth(int month) {
		switch (month){
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

	public int getId(){
		return this.databaseID;
	}
}
