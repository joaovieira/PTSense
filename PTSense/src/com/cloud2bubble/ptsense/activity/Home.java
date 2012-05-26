package com.cloud2bubble.ptsense.activity;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cloud2bubble.ptsense.PTSense;
import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.dialog.SensingManager;
import com.cloud2bubble.ptsense.dialog.StartSensingDialog;
import com.cloud2bubble.ptsense.dialog.StopSensingDialog;
import com.cloud2bubble.ptsense.sensingservice.SmartphoneSensingService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends SherlockFragmentActivity implements OnClickListener, SensingManager {

	RelativeLayout bOption1, bOption2, bOption3;
	Button bToggleSensing;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bOption1 = (RelativeLayout) findViewById(R.id.bHomeOption1);
		TextView tvTitle1 = (TextView) findViewById(R.id.tvHomeButtonSensing)
				.findViewById(android.R.id.text1);
		TextView tvSubTitle1 = (TextView) findViewById(R.id.tvHomeButtonSensing)
				.findViewById(android.R.id.text2);
		tvTitle1.setText(R.string.home_title1);
		tvSubTitle1.setText(R.string.home_subtitle1);
		bToggleSensing = (Button) findViewById(R.id.bToggleSensing);
		bOption1.setOnClickListener(this);

		bOption2 = (RelativeLayout) findViewById(R.id.bHomeOption2);
		TextView tvTitle2 = (TextView) findViewById(R.id.tvHomeButtonTrips)
				.findViewById(android.R.id.text1);
		TextView tvSubTitle2 = (TextView) findViewById(R.id.tvHomeButtonTrips)
				.findViewById(android.R.id.text2);
		tvTitle2.setText(R.string.home_title2);
		tvSubTitle2.setText(R.string.home_subtitle2);

		bOption3 = (RelativeLayout) findViewById(R.id.bHomeOption3);
		TextView tvTitle3 = (TextView) findViewById(R.id.tvHomeButtonPlan)
				.findViewById(android.R.id.text1);
		TextView tvSubTitle3 = (TextView) findViewById(R.id.tvHomeButtonPlan)
				.findViewById(android.R.id.text2);
		tvTitle3.setText(R.string.home_title3);
		tvSubTitle3.setText(R.string.home_subtitle3);

		bOption2.setOnClickListener(this);
		bOption3.setOnClickListener(this);
		bToggleSensing.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		toggleSensingButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.home_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miMyProfile:
			startActivity(new Intent(this, MyProfile.class));
			return true;
		case R.id.miSettings:
			startActivity(new Intent(this, Settings.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bHomeOption1:
			startActivity(new Intent(this, Sensing.class));
			break;
		case R.id.bHomeOption2:
			startActivity(new Intent(this, TripReviews.class));
			break;
		case R.id.bHomeOption3:
			startActivity(new Intent(this, PlanTrip.class));
			break;
		case R.id.bToggleSensing:
			PTSense app = (PTSense) getApplication();
			if (!app.isSensing()) {
				showSenseDialog(PTSense.DIALOG_START_SENSING);
			} else {
				showSenseDialog(PTSense.DIALOG_STOP_SENSING);
			}
			break;
		}
	}

	private void toggleSensingButtons() {
		PTSense app = (PTSense) getApplication();
		if (app.isSensing()) {
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
		case PTSense.DIALOG_START_SENSING:
			SherlockDialogFragment startDialogFragment = new StartSensingDialog(this);
			startDialogFragment.show(getSupportFragmentManager(), "start_dialog");
			break;
		case PTSense.DIALOG_STOP_SENSING:
			SherlockDialogFragment stopDialogFragment = StopSensingDialog
					.newInstance(this);
			stopDialogFragment.show(getSupportFragmentManager(), "stop_dialog");
			break;
		}
	}

	public void doPositiveClick(int dialog, int state) {
		final PTSense app = (PTSense) getApplication();
		switch (dialog) {
		case PTSense.DIALOG_START_SENSING:
			if (state == PTSense.STATE_STOPPED) {
				app.setState(PTSense.STATE_SENSING);
				startActivity(new Intent(Home.this, Sensing.class));
				startService(new Intent(Home.this,
						SmartphoneSensingService.class));
			} else {
				app.setState(PTSense.STATE_STOPPED);
				stopSensing();
			}
			break;
		case PTSense.DIALOG_STOP_SENSING:
			if (app.isTripInfoCompleted()) {
				app.setState(PTSense.STATE_STOPPED);
				stopSensing();
			} else {
				DialogFragment startDialogFragment = new StartSensingDialog(
						this, "stop");
				startDialogFragment.show(getSupportFragmentManager(), "start_dialog");
			}
			break;
		}
	}

	public void stopSensing() {
		stopService(new Intent(Home.this, SmartphoneSensingService.class));
		toggleSensingButtons();
	}

}