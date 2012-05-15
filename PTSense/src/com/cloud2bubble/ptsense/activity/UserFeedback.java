package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UserFeedback extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_feedback);
		
		ActionBar actionBar = getActionBar();

		View actionCustomView = getLayoutInflater().inflate(
				R.layout.action_mode_bar, null);

		actionBar.setCustomView(actionCustomView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		ReviewItem trip = (ReviewItem) extras.get("review_item");
		if (trip != null) {
			TextView tvService = (TextView) findViewById(R.id.tvService);
			TextView tvDirection = (TextView) findViewById(R.id.tvDirection);
			TextView tvDate = (TextView) findViewById(R.id.tvDate);
			TextView tvCloseButton = (TextView) findViewById(R.id.tvActionModeCloseButton);
			
			tvService.setText(trip.serviceToString());
			tvDirection.setText(trip.directionToString());
			tvDate.setText(trip.dateToString());
			
			tvCloseButton.setOnClickListener(this);
			//android.R.attr.textColo
		}
	}

	public void onClick(View view) {
		switch (view.getId()){
		case R.id.tvActionModeCloseButton:
			Intent data = new Intent();
			setResult(RESULT_OK, data);
			finish();
			break;
		}
	}
}
