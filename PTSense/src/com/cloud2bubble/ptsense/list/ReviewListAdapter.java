package com.cloud2bubble.ptsense.list;

import java.util.ArrayList;

import com.cloud2bubble.ptsense.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReviewListAdapter extends ArrayAdapter<ReviewItem> {

	private ArrayList<ReviewItem> items;
	private LayoutInflater vi;

	public ReviewListAdapter(Context context, ArrayList<ReviewItem> items) {
		super(context, 0, items);
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
