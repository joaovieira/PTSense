package com.cloud2bubble.ptsense;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NowFragment extends Fragment {
	
	//TextView x,y,z;
	TextView test;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.now_fragment, container, false);
		test = (TextView) v.findViewById(R.id.tvTestSensors);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		if(SmartphoneSensingService.IS_RUNNING){
			test.setText(String.valueOf(SmartphoneSensingService.lightValues.get(0)));
		}
	}
}
