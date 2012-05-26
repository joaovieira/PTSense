package com.cloud2bubble.ptsense.tabfragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.cloud2bubble.ptsense.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RoutinesFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.feature_not_supported, container,
				false);

		TextView text = (TextView) v.findViewById(android.R.id.text1);
		text.setText("Here you will find your manually and automatically defined travelling routines.");

		return v;
	}

}
