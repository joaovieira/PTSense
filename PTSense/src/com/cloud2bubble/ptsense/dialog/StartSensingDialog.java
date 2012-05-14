package com.cloud2bubble.ptsense.dialog;

import com.cloud2bubble.ptsense.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

public class StartSensingDialog extends DialogFragment {

	static Activity activity;

	public static StartSensingDialog newInstance(Context cxt, int title) {
		StartSensingDialog frag = new StartSensingDialog();
		activity = (Activity) cxt;
		return frag;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
				android.R.style.Theme_Holo_Dialog))
				.setMessage("Start sensing conditions now?")
				.setCancelable(true)
				.setPositiveButton(R.string.start,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								((SensingManager) activity).doPositiveClick(SensingManager.DIALOG_START_SENSING);
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

