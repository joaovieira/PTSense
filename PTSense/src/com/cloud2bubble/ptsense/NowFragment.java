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
import android.widget.LinearLayout;
import android.widget.TextView;

public class NowFragment extends Fragment {

	private Activity sensingActivity;

	// remove
	LinearLayout sensingNowEnvironment;
	TextView tvLight, tvAccelX, tvAccelY, tvAccelZ, tvPressure, tvHumidity,
			tvTemperature;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.now_fragment, container, false);

		sensingActivity = getActivity();

		sensingNowEnvironment = (LinearLayout) v.findViewById(R.id.llSensingNow);

		if (SmartphoneSensingService.mAcceleration != null) {
			tvAccelX = new TextView(sensingActivity);
			sensingNowEnvironment.addView(tvAccelX);
			tvAccelY = new TextView(sensingActivity);
			sensingNowEnvironment.addView(tvAccelY);
			tvAccelZ = new TextView(sensingActivity);
			sensingNowEnvironment.addView(tvAccelZ);
		}

		if (SmartphoneSensingService.mAmbTemperature != null) {
			tvTemperature = new TextView(sensingActivity);
			sensingNowEnvironment.addView(tvTemperature);
		}

		if (SmartphoneSensingService.mLight != null) {
			tvLight = new TextView(sensingActivity);
			sensingNowEnvironment.addView(tvLight);
		}

		if (SmartphoneSensingService.mPressure != null) {
			tvPressure = new TextView(sensingActivity);
			sensingNowEnvironment.addView(tvPressure);
		}

		if (SmartphoneSensingService.mRelHumidity != null) {
			tvHumidity = new TextView(sensingActivity);
			sensingNowEnvironment.addView(tvHumidity);
		}

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
    	String sensedData = "";
    	if((sensedData = intent.getStringExtra("accelX")) != null)
    		tvAccelX.setText(sensedData);

    	if((sensedData = intent.getStringExtra("accelY")) != null)
    		tvAccelY.setText(sensedData);
    	
    	if((sensedData = intent.getStringExtra("accelZ")) != null)
    		tvAccelZ.setText(sensedData);
    	
    	if((sensedData = intent.getStringExtra("temperature")) != null)
    		tvTemperature.setText(sensedData);

    	if((sensedData = intent.getStringExtra("light")) != null)
    		tvLight.setText(sensedData);
    	
    	if((sensedData = intent.getStringExtra("pressure")) != null)
    		tvPressure.setText(sensedData);
    	
    	if((sensedData = intent.getStringExtra("humidity")) != null)
    		tvHumidity.setText(sensedData);
    }
}
