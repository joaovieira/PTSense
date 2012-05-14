package com.cloud2bubble.ptsense.list;

import java.util.ArrayList;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.activity.TripReviews;
import com.cloud2bubble.ptsense.activity.UserFeedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReviewListAdapter extends ArrayAdapter<ReviewItem> {

	private ArrayList<ReviewItem> items;
	private LayoutInflater vi;
	private Context context;

	public ReviewListAdapter(Context context, ArrayList<ReviewItem> items) {
		super(context, 0, items);
		this.context = context;
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		final ReviewItem ri = items.get(position);
		if (ri != null) {
			switch (ri.getType()){
			case ReviewItem.USER_FEEDBACK:
				v = vi.inflate(R.layout.userfeedback_list_item, null);
				final TextView service = (TextView) v.findViewById(R.id.tvService);
				final TextView direction = (TextView) v.findViewById(R.id.tvDirection);
				final TextView date = (TextView) v.findViewById(R.id.tvDate);
				
				v.setOnClickListener(new OnClickListener(){
				        public void onClick(View v) {
				        	Intent i = new Intent(context, UserFeedback.class);
				        	i.putExtra("review_item", ri);
				    		((Activity) context).startActivityForResult(i, TripReviews.REQUEST_FEEDBACK_CODE);
				        }
				});
				
				if (service != null)
					service.setText(ri.serviceToString());
				if (direction != null)
					direction.setText(ri.directionToString());
				if (date != null)
					date.setText(ri.dateToString());
				break;
			case ReviewItem.SYSTEM_REVIEW:
				break;
			}
		}
		return v;
	}

}
