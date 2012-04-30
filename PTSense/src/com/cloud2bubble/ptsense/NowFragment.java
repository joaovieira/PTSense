package com.cloud2bubble.ptsense;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NowFragment extends Fragment {
	
	//TextView x,y,z;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO bind TextViews variables with layout
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.now_fragment, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		// TODO update TextViews with SmartphoneSensingService variables
	}
}
