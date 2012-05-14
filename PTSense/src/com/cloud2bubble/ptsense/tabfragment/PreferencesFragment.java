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
		return inflater.inflate(R.layout.preferences_fragment, container, false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(0, Menu.NONE, 1, "Edit")
				.setIcon(R.drawable.ic_action_edit)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
}
