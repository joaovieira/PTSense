package com.cloud2bubble.ptsense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity implements OnClickListener {
	
	Button bOption1, bOption2, bOption3, bToggleSensing;
	boolean isSensing;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		bOption1 = (Button) findViewById(R.id.bHomeOption1);
		bOption2 = (Button) findViewById(R.id.bHomeOption2);
		bOption3 = (Button) findViewById(R.id.bHomeOption3);
		bToggleSensing = (Button) findViewById(R.id.bToggleSensing);
		bOption1.setOnClickListener(this);
		bOption2.setOnClickListener(this);
		bOption3.setOnClickListener(this);
		bToggleSensing.setOnClickListener(this);	
		isSensing = SmartphoneSensingService.IS_RUNNING;
	}

	@Override
	protected void onResume() {
		super.onResume();
		toggleSensingButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miMyProfile:
			Intent myProfileIntent = new Intent(this, MyProfile.class);
			startActivity(myProfileIntent);
			return true;
		case R.id.miSettings:
			Intent settingsIntent = new Intent(this, Settings.class);
			startActivity(settingsIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bHomeOption1:
			Intent option1Intent = new Intent(this, Sensing.class);
			startActivity(option1Intent);
			break;
		case R.id.bHomeOption2:
			Intent option2Intent = new Intent(this, TripReviews.class);
			startActivity(option2Intent);
			break;
		case R.id.bHomeOption3:
			Intent option3Intent = new Intent(this, PlanTrip.class);
			startActivity(option3Intent);
			break;
		case R.id.bToggleSensing:
			if(!isSensing){
				isSensing = true;
				Intent i=new Intent(this, SmartphoneSensingService.class);
			    startService(i);
			    Intent sensingIntent = new Intent(this, Sensing.class);
				startActivity(sensingIntent);
			}else{
				isSensing = false;
				stopService(new Intent(this, SmartphoneSensingService.class));
			}
			break;
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		isSensing = savedInstanceState.getBoolean("isSensing");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("isSensing", isSensing);
	}
	
	private void toggleSensingButtons() {
		if(isSensing){
			bOption1.setEnabled(true);
			bToggleSensing.setText(getText(R.string.stop));
		}else{
			bOption1.setEnabled(false);
			bToggleSensing.setText(getText(R.string.start));
		}
	}
}