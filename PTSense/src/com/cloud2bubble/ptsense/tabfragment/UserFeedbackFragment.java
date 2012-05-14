package com.cloud2bubble.ptsense.tabfragment;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.list.EntryItem;
import com.cloud2bubble.ptsense.list.ReviewItem;
import com.cloud2bubble.ptsense.list.ReviewListAdapter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class UserFeedbackFragment extends Fragment {
	
	private Activity tripReviewsActivity;
	
	ArrayList<ReviewItem> items;
	ReviewListAdapter adapter;
	
	TextView tvEmptyList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tripReviewsActivity = getActivity();

		items = new ArrayList<ReviewItem>();
		
		// TODO fetch user feedbacks from database and add to items
		
		items.add(new ReviewItem("District Line", "Transport For London - Underground", 
				"Paddingtion", "Bayswater", new GregorianCalendar()));
		items.add(new ReviewItem("436 Line", "Transport For London - Buses", 
				"Victoria", "Marble Arch (Park Lane)", new GregorianCalendar()));
		
		adapter = new ReviewListAdapter(tripReviewsActivity, items);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.review_list_fragment, container, false);
        
        ListView list = (ListView) v.findViewById(R.id.lvReviewList);
        tvEmptyList = (TextView) v.findViewById(R.id.tvEmptyList);

		list.setAdapter(adapter);
		
		if(items.isEmpty()){
			tvEmptyList.setText(R.string.empty_feedback_list);
			tvEmptyList.setVisibility(View.VISIBLE);
		}
		
		return v;
	}
}
