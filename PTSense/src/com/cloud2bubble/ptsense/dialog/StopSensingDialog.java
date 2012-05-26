package com.cloud2bubble.ptsense.dialog;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.cloud2bubble.ptsense.PTSense;
import com.cloud2bubble.ptsense.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

public class StopSensingDialog extends SherlockDialogFragment {

	static Activity activity;

	public static StopSensingDialog newInstance(Context cxt) {
		StopSensingDialog frag = new StopSensingDialog();
		activity = (Activity) cxt;
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
				android.R.style.Theme_Holo_Dialog))
				.setMessage("This will stop sensing conditions. Do you want to continue?")
				.setCancelable(true)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								((SensingManager) activity).doPositiveClick(PTSense.DIALOG_STOP_SENSING, 0);
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
