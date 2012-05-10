package com.cloud2bubble.ptsense;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Sensing extends Activity{
	
	//remove
	LinearLayout sensingNowEnvironment;
	TextView tvLight, tvAccelX, tvAccelY, tvAccelZ, tvPressure, tvHumidity, tvTemperature;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment
		
		/*
		// setup action bar for tabs
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    Tab tab = actionBar.newTab()
	            .setText(R.string.sensing_tab1)
	            .setTabListener(new MyTabListener<ThisLineFragment>(
	                    this, "this_line", ThisLineFragment.class));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	        .setText(R.string.sensing_tab2)
	        .setTabListener(new MyTabListener<NowFragment>(
	                this, "now", NowFragment.class));
	    actionBar.addTab(tab);
	    
	    actionBar.setDisplayHomeAsUpEnabled(true);*/
		
		
		/* ------- START REMOVE ---------- */
		setContentView(R.layout.now_fragment);
			
		sensingNowEnvironment = (LinearLayout) findViewById(R.id.llSensingNow);
		
		if (SmartphoneSensingService.mLinearAcceleration != null){
			tvAccelX = new TextView(this);
			tvAccelX.setText("0.0");
			sensingNowEnvironment.addView(tvAccelX);
			tvAccelY = new TextView(this);
			tvAccelY.setText("0.0");
			sensingNowEnvironment.addView(tvAccelY);
			tvAccelZ = new TextView(this);
			tvAccelZ.setText("0.0");
			sensingNowEnvironment.addView(tvAccelZ);
		}
		
		if (SmartphoneSensingService.mAmbTemperature != null){
			tvTemperature = new TextView(this);
			tvTemperature.setText("0.0");
			sensingNowEnvironment.addView(tvTemperature);
		}
			
		if (SmartphoneSensingService.mLight != null){
			tvLight = new TextView(this);
			tvLight.setText("0.0");
			sensingNowEnvironment.addView(tvLight);
		}
		
		if (SmartphoneSensingService.mPressure != null){
			tvPressure = new TextView(this);
			tvPressure.setText("0.0");
			sensingNowEnvironment.addView(tvPressure);
		}
		
		if (SmartphoneSensingService.mRelHumidity != null){
			tvHumidity = new TextView(this);
			tvHumidity.setText("0.0");
			sensingNowEnvironment.addView(tvHumidity);
		}
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
		registerReceiver(broadcastReceiver, new IntentFilter(SmartphoneSensingService.BROADCAST_ACTION));
	}
    
    @Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
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
    
    /* ------- STOP REMOVE ---------- */


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sensing_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent homeIntent = new Intent(this, Home.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			return true;
		case R.id.miSettingsSensing:
			Intent settingsIntent = new Intent(this, Settings.class);
			startActivity(settingsIntent);
			return true;
		case R.id.miSetupDevices:
			//TODO open Setup Devices preference pane
			Intent devicesIntent = new Intent(this, Settings.class);
			startActivity(devicesIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
