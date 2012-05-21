package com.cloud2bubble.ptsense.list;

import java.util.Calendar;

public class SystemReviewItem extends ReviewItem {

	private static final long serialVersionUID = 1L;

	public SystemReviewItem(String line, String service, String origin,
			String destination, Calendar startTime, Calendar endTime) {
		super(line, service, origin, destination, startTime, endTime);
	}

	@Override
	public int getType() {
		return ReviewItem.SYSTEM_REVIEW;
	}
}
