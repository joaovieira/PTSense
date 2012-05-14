package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.tabfragment.SystemReviewsFragment;
import com.cloud2bubble.ptsense.tabfragment.UserFeedbackFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class TripReviews extends Activity {
	
	public static final int REQUEST_FEEDBACK_CODE = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment
		
		// setup action bar for tabs
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    Tab tab = actionBar.newTab()
	            .setText(R.string.trip_reviews_tab1)
	            .setTabListener(new MyTabListener<SystemReviewsFragment>(
	                    this, "system_reviews", SystemReviewsFragment.class));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	        .setText(R.string.trip_reviews_tab2)
	        .setTabListener(new MyTabListener<UserFeedbackFragment>(
	                this, "user_feedback", UserFeedbackFragment.class));
	    actionBar.addTab(tab);
	    
	    actionBar.setDisplayHomeAsUpEnabled(true);
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_FEEDBACK_CODE) {
			Toast.makeText(this, R.string.feedback_saved,
						Toast.LENGTH_SHORT).show();
		}
	}
}
