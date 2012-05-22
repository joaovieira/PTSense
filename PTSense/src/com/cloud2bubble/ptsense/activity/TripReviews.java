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

public class TripReviews extends Activity {

	public static final int REQUEST_FEEDBACK_CODE = 9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Notice that setContentView() is not used, because we use the root
		// android.R.id.content as the container for each fragment

		// setup action bar for tabs
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tab = actionBar
				.newTab()
				.setText(R.string.trip_reviews_tab1)
				.setTabListener(
						new MyTabListener<SystemReviewsFragment>(this,
								"system_reviews", SystemReviewsFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.trip_reviews_tab2)
				.setTabListener(
						new MyTabListener<UserFeedbackFragment>(this,
								"user_feedback", UserFeedbackFragment.class));
		actionBar.addTab(tab);
		
		Intent i = getIntent();
		if (i != null){
			int tabSelector = i.getIntExtra("tab", 0);
			actionBar.setSelectedNavigationItem(tabSelector);
		}

		actionBar.setDisplayHomeAsUpEnabled(true);
		
		/*ReviewItem item1 = new ReviewItem("District Line", "Transport For London - Underground", 
				"Paddington", "Bayswater", new GregorianCalendar());
		ReviewItem item2 = new ReviewItem("436 Line", "Transport For London - Buses", 
				"Victoria", "Marble Arch (Park Lane)", new GregorianCalendar());
		ReviewItem item3 = new ReviewItem("Jubilee Line", "Transport For London - Underground", 
				"Stratford", "Canary Wharf", new GregorianCalendar());
		ReviewItem item4 = new ReviewItem("First Capital Connect", "National Railways", 
				"Palmers Green", "Old Street", new GregorianCalendar());
		database.addPendingReview(item1);
		database.addPendingReview(item2);
		database.addPendingReview(item3);
		database.addPendingReview(item4);*/
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
		if (resultCode == Activity.RESULT_OK
				&& requestCode == REQUEST_FEEDBACK_CODE) {
		}
	}
}
