package com.cloud2bubble.ptsense.list;

import java.util.Calendar;

public class SystemReviewItem extends ReviewItem {

	public SystemReviewItem(String line, String service, String origin, String destination, Calendar date) {
		super(line, service, origin, destination, date);
	}

	@Override
	public int getType() {
		return ReviewItem.SYSTEM_REVIEW;
	}
}
