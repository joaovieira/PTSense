package com.cloud2bubble.ptsense.activity;

import java.util.GregorianCalendar;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.database.TripFeedback;
import com.cloud2bubble.ptsense.list.ReviewItem;
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
	private DatabaseHandler database;

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

		actionBar.setDisplayHomeAsUpEnabled(true);
		database = new DatabaseHandler(this);
		
		/*ReviewItem item1 = new ReviewItem("District Line", "Transport For London - Underground", 
				"Paddingtion", "Bayswater", new GregorianCalendar());
		ReviewItem item2 = new ReviewItem("436 Line", "Transport For London - Buses", 
				"Victoria", "Marble Arch (Park Lane)", new GregorianCalendar());
		database.addReview(item1);
		database.addReview(item2);*/
		//database.clearTables();
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
			Bundle extras = data.getExtras();
			if (extras == null) {
				return;
			}
			TripFeedback feedback = (TripFeedback) extras.get("feedback");
			if (feedback != null) {
				if (insertFeedbackIntoDatabase(feedback))
					Toast.makeText(this, R.string.feedback_saved,
							Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public DatabaseHandler getDatabase(){
		return database;
	}

	private boolean insertFeedbackIntoDatabase(TripFeedback feedback) {
		ReviewItem oldReview = feedback.getTrip();
		database.removeReview(oldReview);
		return true;
	}
}
