package com.cloud2bubble.ptsense;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NowFragment extends Fragment {
	
	//TextView x,y,z;
	private Activity sensingActivity;
	
	TextView test;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.now_fragment, container, false);
		
		sensingActivity = getActivity();
		return v;
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	updateUI(intent); 
        }
    };
    
    @Override
	public void onResume() {
		super.onResume();
		sensingActivity.registerReceiver(broadcastReceiver, new IntentFilter(SmartphoneSensingService.BROADCAST_ACTION));
	}
    
    @Override
	public void onPause() {
		super.onPause();
		sensingActivity.unregisterReceiver(broadcastReceiver);
	}
    
    private void updateUI(Intent intent) {
    	String light = intent.getStringExtra("light");

    	test.setText(light);
    }
}
