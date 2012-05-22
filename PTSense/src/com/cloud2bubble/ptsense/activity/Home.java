package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.dialog.SensingManager;
import com.cloud2bubble.ptsense.dialog.StartSensingDialog;
import com.cloud2bubble.ptsense.dialog.StopSensingDialog;
import com.cloud2bubble.ptsense.sensingservice.SmartphoneSensingService;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends Activity implements OnClickListener, SensingManager {

	RelativeLayout bOption1, bOption2, bOption3;
	TextView tvTitle1, tvTitle2, tvTitle3;
	TextView tvSubTitle1, tvSubTitle2, tvSubTitle3;
	Button bToggleSensing;
	boolean isSensing;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		bOption1 = (RelativeLayout) findViewById(R.id.bHomeOption1);
		tvTitle1 = (TextView) findViewById(R.id.tvHomeButtonSensing)
				.findViewById(android.R.id.text1);
		tvSubTitle1 = (TextView) findViewById(R.id.tvHomeButtonSensing)
				.findViewById(android.R.id.text2);
		tvTitle1.setText(R.string.home_title1);
		tvSubTitle1.setText(R.string.home_subtitle1);
		bToggleSensing = (Button) findViewById(R.id.bToggleSensing);
		bOption1.setOnClickListener(this);

		bOption2 = (RelativeLayout) findViewById(R.id.bHomeOption2);
		tvTitle2 = (TextView) findViewById(R.id.tvHomeButtonTrips)
				.findViewById(android.R.id.text1);
		tvSubTitle2 = (TextView) findViewById(R.id.tvHomeButtonTrips)
				.findViewById(android.R.id.text2);
		tvTitle2.setText(R.string.home_title2);
		tvSubTitle2.setText(R.string.home_subtitle2);

		bOption3 = (RelativeLayout) findViewById(R.id.bHomeOption3);
		tvTitle3 = (TextView) findViewById(R.id.tvHomeButtonPlan).findViewById(
				android.R.id.text1);
		tvSubTitle3 = (TextView) findViewById(R.id.tvHomeButtonPlan)
				.findViewById(android.R.id.text2);
		tvTitle3.setText(R.string.home_title3);
		tvSubTitle3.setText(R.string.home_subtitle3);

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
			if (!isSensing) {
				showSenseDialog(SensingManager.DIALOG_START_SENSING);
			} else {
				showSenseDialog(SensingManager.DIALOG_STOP_SENSING);
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
		if (isSensing) {
			bOption1.setEnabled(true);
			bToggleSensing.setText(getText(R.string.stop));
			bToggleSensing.setBackgroundResource(R.color.sense_button_stop);
		} else {
			bOption1.setEnabled(false);
			bToggleSensing.setText(getText(R.string.start));
			bToggleSensing.setBackgroundResource(R.color.sense_button_start);
		}
	}

	public void showSenseDialog(int dialog) {
		switch (dialog) {
		case SensingManager.DIALOG_START_SENSING:
			DialogFragment startDialogFragment = StartSensingDialog
					.newInstance(this);
			startDialogFragment.show(getFragmentManager(), "start_dialog");
			break;
		case SensingManager.DIALOG_STOP_SENSING:
			DialogFragment stopDialogFragment = StopSensingDialog
					.newInstance(this);
			stopDialogFragment.show(getFragmentManager(), "stop_dialog");
			break;
		}
	}

	public void doPositiveClick(int dialog, Bundle bundle) {
		switch (dialog) {
		case SensingManager.DIALOG_START_SENSING:
			isSensing = true;
			Intent i = new Intent(this, SmartphoneSensingService.class);
			if (bundle != null)
				i.putExtra("trip_info", bundle);
			startService(i);
			Intent sensingIntent = new Intent(this, Sensing.class);
			startActivity(sensingIntent);
			break;
		case SensingManager.DIALOG_STOP_SENSING:
			stopService(new Intent(Home.this, SmartphoneSensingService.class));
			isSensing = false;
			toggleSensingButtons();
			break;
		}
	}
}