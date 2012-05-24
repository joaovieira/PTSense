package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.PTSense;
import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.dialog.SensingManager;
import com.cloud2bubble.ptsense.dialog.StartSensingDialog;
import com.cloud2bubble.ptsense.dialog.StopSensingDialog;
import com.cloud2bubble.ptsense.sensingservice.SmartphoneSensingService;
import com.cloud2bubble.ptsense.tabfragment.NowFragment;
import com.cloud2bubble.ptsense.tabfragment.ThisLineFragment;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Sensing extends Activity implements SensingManager {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tab = actionBar
				.newTab()
				.setText(R.string.sensing_tab1)
				.setTabListener(
						new MyTabListener<ThisLineFragment>(this, "this_line",
								ThisLineFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.sensing_tab2)
				.setTabListener(
						new MyTabListener<NowFragment>(this, "now",
								NowFragment.class));
		actionBar.addTab(tab);

		Intent i = getIntent();
		if (i != null){
			int tabSelector = i.getIntExtra("tab", 0);
			actionBar.setSelectedNavigationItem(tabSelector);
		}
		
		// open in second tab
		actionBar.setSelectedNavigationItem(1);

		actionBar.setDisplayHomeAsUpEnabled(true);
	}

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
		case R.id.miStopSensing:
			showSenseDialog(PTSense.DIALOG_STOP_SENSING);
			return true;
		case android.R.id.home:
			Intent homeIntent = new Intent(this, Home.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			return true;
		case R.id.miEditJourneyInfo:
			showSenseDialog(PTSense.DIALOG_START_SENSING);
			return true;
		case R.id.miSetupDevices:
			Intent devicesIntent = new Intent(this, Settings.class);
			devicesIntent.putExtra("open_devices", true);
			startActivity(devicesIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showSenseDialog(int dialog) {
		switch (dialog) {
		case PTSense.DIALOG_START_SENSING:
			DialogFragment startDialogFragment = new StartSensingDialog(this);
			startDialogFragment.show(getFragmentManager(), "start_dialog");
			break;
		case PTSense.DIALOG_STOP_SENSING:
			DialogFragment stopDialogFragment = StopSensingDialog
					.newInstance(this);
			stopDialogFragment.show(getFragmentManager(), "stop_dialog");
			break;
		}
	}

	public void doPositiveClick(int dialog, int state) {
		PTSense app = (PTSense) getApplicationContext();

		switch (dialog) {
		case PTSense.DIALOG_START_SENSING:
			app.setState(PTSense.STATE_STOPPED);
			stopSensing();
			break;
		case PTSense.DIALOG_STOP_SENSING:
			if (app.isTripInfoCompleted()) {
				app.setState(PTSense.STATE_STOPPED);
				stopSensing();
			} else {
				DialogFragment startDialogFragment = new StartSensingDialog(
						this, "stop");
				startDialogFragment.show(getFragmentManager(), "start_dialog");
			}
			break;
		}
	}

	public void stopSensing() {
		stopService(new Intent(this, SmartphoneSensingService.class));
		Intent homeIntent = new Intent(this, Home.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

}
