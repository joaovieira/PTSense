package com.cloud2bubble.ptsense.dialog;

import java.util.Map;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.cloud2bubble.ptsense.PTSense;
import com.cloud2bubble.ptsense.PTService;
import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class StartSensingDialog extends SherlockDialogFragment implements
		OnClickListener {

	Activity activity;
	Map<String, String[]> serviceLines;
	AutoCompleteTextView selectOrigin, selectDestination, selectLine;
	Button bStart;
	String selectedService;

	int stateApp;

	public StartSensingDialog(Context cxt) {
		this.activity = (Activity) cxt;
		PTSense app = (PTSense) activity.getApplication();
		this.stateApp = app.getState();
	}
	
	public StartSensingDialog(Context cxt, String stop) {
		this.activity = (Activity) cxt;
		this.stateApp = -1;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(SherlockDialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Dialog);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.start_sensing_dialog, container,
				false);
		getDialog().setTitle(R.string.start_dialog_title);
		setCancelable(true);
		
		PTSense app = (PTSense) activity.getApplication();
		ReviewItem currentTrip = app.getCurrentTrip();

		TextView serviceTitle = (TextView) v.findViewById(R.id.tvService);
		TextView lineTitle = (TextView) v.findViewById(R.id.tvLine);
		TextView originTitle = (TextView) v.findViewById(R.id.tvOrigin);
		TextView destinationTitle = (TextView) v
				.findViewById(R.id.tvDestination);
		serviceTitle.setText(R.string.service);
		lineTitle.setText(R.string.line);
		originTitle.setText(R.string.origin);
		destinationTitle.setText(R.string.destination);

		Button bCancel = (Button) v.findViewById(android.R.id.button2);
		bStart = (Button) v.findViewById(android.R.id.button1);
		bCancel.setText(R.string.alert_dialog_cancel);
		if (stateApp == PTSense.STATE_STOPPED) {
			bStart.setText(R.string.start);
		} else if (stateApp == PTSense.STATE_SENSING) {
			bStart.setText(R.string.done);
		} else {
			bStart.setText(R.string.stop);
			bStart.setEnabled(false);
		}

		selectOrigin = (AutoCompleteTextView) v.findViewById(R.id.acOrigin);
		selectDestination = (AutoCompleteTextView) v
				.findViewById(R.id.acDestination);
		selectLine = (AutoCompleteTextView) v.findViewById(R.id.acLine);
		Spinner selectService = (Spinner) v.findViewById(R.id.sService);
		ArrayAdapter<String> adapterService = new ArrayAdapter<String>(
				activity, R.layout.autocomplete_list_item, PTService.SERVICES
						.keySet().toArray(new String[0]));
		selectService.setAdapter(adapterService);

		selectOrigin.setHint(R.string.autocomplete_origin_hint);
		selectDestination.setHint(R.string.autocomplete_destination_hint);
		selectLine.setHint(R.string.autocomplete_line_hint);

		selectOrigin.setEnabled(false);
		selectDestination.setEnabled(false);
		selectLine.setEnabled(false);

		if (stateApp != PTSense.STATE_STOPPED) {
			if (!currentTrip.service.equals("")) {
				selectedService = currentTrip.service;
				int pos = adapterService.getPosition(selectedService);
				selectService.setSelection(pos, true);

				serviceLines = PTService.SERVICES.get(selectedService);
				ArrayAdapter<String> adapterOrigin = new ArrayAdapter<String>(
						activity, R.layout.autocomplete_list_item, serviceLines
								.keySet().toArray(new String[0]));
				selectLine.setAdapter(adapterOrigin);
				selectLine.setEnabled(true);
			}

			if (!currentTrip.line.equals("")) {
				selectLine.setText(currentTrip.line);
				selectOrigin.setEnabled(true);
				selectDestination.setEnabled(true);

				String[] stops = serviceLines.get(currentTrip.line);
				ArrayAdapter<String> adapterStops = new ArrayAdapter<String>(
						activity, R.layout.autocomplete_list_item, stops);
				selectOrigin.setAdapter(adapterStops);
				selectDestination.setAdapter(adapterStops);
			}

			if (!currentTrip.origin.equals(""))
				selectOrigin.setText(currentTrip.origin);

			if (!currentTrip.destination.equals(""))
				selectDestination.setText(currentTrip.destination);

			if (stateApp == -1) {
				TextWatcher watcher = new LocalTextWatcher();
				selectOrigin.addTextChangedListener(watcher);
				selectDestination.addTextChangedListener(watcher);
				selectLine.addTextChangedListener(watcher);
			}
		}
		selectLine.setThreshold(1);
		selectLine.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				String[] stops = serviceLines.get(((TextView) view).getText());

				ArrayAdapter<String> adapterStops = new ArrayAdapter<String>(
						activity, R.layout.autocomplete_list_item, stops);
				selectOrigin.setAdapter(adapterStops);
				selectDestination.setAdapter(adapterStops);
				clearStops();
				selectOrigin.setEnabled(true);
				selectDestination.setEnabled(true);
			}
		});

		selectService.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> list, View view, int pos,
					long arg3) {
				selectedService = list.getItemAtPosition(pos).toString();
				serviceLines = PTService.SERVICES.get(selectedService);
				ArrayAdapter<String> adapterOrigin = new ArrayAdapter<String>(
						activity, R.layout.autocomplete_list_item, serviceLines
								.keySet().toArray(new String[0]));
				selectLine.setAdapter(adapterOrigin);
				clearLine();
				clearStops();
				selectOrigin.setEnabled(false);
				selectDestination.setEnabled(false);
				selectLine.setEnabled(true);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		bCancel.setOnClickListener(this);
		bStart.setOnClickListener(this);
		return v;
	}

	protected void clearStops() {
		selectOrigin.setText("");
		selectDestination.setText("");
	}

	protected void clearLine() {
		selectLine.setText("");
	}

	private void updateButtonState() {
		boolean enabled = checkEditText(selectOrigin)
				&& checkEditText(selectDestination)
				&& checkEditText(selectLine);
		bStart.setEnabled(enabled);
	}

	private boolean checkEditText(AutoCompleteTextView view) {
		return !view.getText().toString().trim().equals("");
	}

	public void onClick(View v) {
		PTSense app = (PTSense) activity.getApplication();
		switch (v.getId()) {
		case android.R.id.button1:
			if (stateApp == PTSense.STATE_STOPPED) {
				String service = selectedService;
				String line = selectLine.getText().toString();
				String origin = selectOrigin.getText().toString();
				String destination = selectDestination.getText().toString();

				app.createTrip(service, line, origin, destination);

				((SensingManager) activity).doPositiveClick(
						PTSense.DIALOG_START_SENSING, stateApp);
			} else {
				String service = selectedService;
				String line = selectLine.getText().toString();
				String origin = selectOrigin.getText().toString();
				String destination = selectDestination.getText().toString();

				app.updateTrip(service, line, origin, destination);

				if (stateApp == -1)
					((SensingManager) activity).doPositiveClick(
							PTSense.DIALOG_START_SENSING, stateApp);
			}
			dismiss();
			break;
		case android.R.id.button2:
			dismiss();
			break;
		}
	}

	private class LocalTextWatcher implements TextWatcher {

		public void afterTextChanged(Editable s) {
			updateButtonState();
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
	}
}
