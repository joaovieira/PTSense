package com.cloud2bubble.ptsense;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

public class StopSensingDialog extends DialogFragment {

	static Activity activity;

	public static StopSensingDialog newInstance(Context cxt, int title) {
		StopSensingDialog frag = new StopSensingDialog();
		activity = (Activity) cxt;
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
				android.R.style.Theme_Holo_Dialog))
				.setMessage("This will stop sensing conditions. Do you want to continue?")
				.setCancelable(true)
				.setPositiveButton(R.string.stop,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								((SensingManager) activity).doPositiveClick();
							}
						})
				.setNegativeButton(R.string.alert_dialog_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dismiss();
							}
						}).create();
	}
}
