package com.cloud2bubble.ptsense.dialog;

public interface SensingManager {

	public void showSenseDialog(int dialog);
	public void doPositiveClick(int dialog, int state);
	public void stopSensing();
}
