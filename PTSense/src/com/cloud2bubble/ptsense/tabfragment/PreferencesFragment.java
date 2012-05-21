package com.cloud2bubble.ptsense.tabfragment;

import com.cloud2bubble.ptsense.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PreferencesFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.feature_not_supported, container, false);
		
		TextView text = (TextView) v.findViewById(android.R.id.text1);
		text.setText("Here you will be able to view and edit all the conditions that define your traveller profile.");
		
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(0, Menu.NONE, 1, "Edit")
				.setIcon(R.drawable.ic_action_edit)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
}
