package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserFeedback extends Activity {
	
	TextView tvService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_feedback);
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		ReviewItem trip = (ReviewItem) extras.get("review_item");
		if (trip != null) {
			tvService = (TextView) findViewById(R.id.tvService);
			tvService.setText(trip.serviceToString());
		}
	}
	
	public void onClick(View view) {
		finish();
	}

	@Override
	public void finish() {
		Intent data = new Intent();
		setResult(RESULT_OK, data);
		super.finish();
	}
}
