package com.cloud2bubble.ptsense.dialog;

public interface SensingManager {
	
	public static final int DIALOG_START_SENSING = 1;
	public static final int DIALOG_STOP_SENSING = 2;

	public void showSenseDialog(int dialog);
	public void doPositiveClick(int dialog);
}
