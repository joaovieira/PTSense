package com.cloud2bubble.ptsense.tabfragment;

import java.util.ArrayList;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.TripReviews;
import com.cloud2bubble.ptsense.activity.UserFeedback;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.list.ReviewItem;
import com.cloud2bubble.ptsense.list.ReviewListAdapter;
import com.cloud2bubble.ptsense.servercommunication.C2BClient;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class UserFeedbackFragment extends Fragment {

	private TripReviews tripReviewsActivity;

	ArrayList<ReviewItem> reviews;
	ReviewListAdapter adapter;

	ListView list;
	TextView tvEmptyList;

	private DatabaseHandler database;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tripReviewsActivity = (TripReviews) getActivity();
		database = DatabaseHandler.getInstance(tripReviewsActivity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.review_list_fragment, container,
				false);

		list = (ListView) v.findViewById(R.id.lvReviewList);
		tvEmptyList = (TextView) v.findViewById(R.id.tvEmptyList);
		
		list.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> adapter, View v, int pos,
					long id) {
				final ReviewItem ri = (ReviewItem)adapter.getItemAtPosition(pos);
				Intent i = new Intent(tripReviewsActivity, UserFeedback.class);
	        	i.putExtra("review_item", ri);
	        	tripReviewsActivity.startActivityForResult(i, TripReviews.REQUEST_FEEDBACK_CODE);
			}
		});

		return v;
	}

	@Override
	public void onResume() {
		NotificationManager nManager = (NotificationManager) tripReviewsActivity
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.cancel(C2BClient.FEEDBACK_NOTIFICATION);

		reviews = database.getAllPendingReviews();
		adapter = new ReviewListAdapter(tripReviewsActivity, reviews);
		list.setAdapter(adapter);

		if (reviews.isEmpty()) {
			tvEmptyList.setText(R.string.empty_feedback_list);
			tvEmptyList.setVisibility(View.VISIBLE);
		}
		super.onResume();
	}

}
