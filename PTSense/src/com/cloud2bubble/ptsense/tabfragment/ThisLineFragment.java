package com.cloud2bubble.ptsense.tabfragment;

import com.cloud2bubble.ptsense.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThisLineFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.feature_not_supported, container, false);
		
		TextView text = (TextView) v.findViewById(android.R.id.text1);
		text.setText("Here you will view all the current conditions of the line you are using (e.g. delay, estimated no. of people)");
		
		return v;
	}
}
