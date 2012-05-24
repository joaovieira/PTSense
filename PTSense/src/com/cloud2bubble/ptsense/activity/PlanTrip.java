package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PlanTrip extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feature_not_supported);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		TextView text = (TextView) findViewById(android.R.id.text1);
		text.setText("Here you will be able to easily plan your trip using system inferences to determine the best routes for you.");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent homeIntent = new Intent(this, Home.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
