package com.cloud2bubble.ptsense.dialog;

import java.util.Map;

import com.cloud2bubble.ptsense.PTService;
import com.cloud2bubble.ptsense.R;

import android.app.Activity;
import android.app.DialogFragment;
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

public class StartSensingDialog extends DialogFragment implements
		OnClickListener {

	static Activity activity;
	Map<String, String[]> serviceLines;
	AutoCompleteTextView selectOrigin, selectDestination, selectLine;
	Button bStart, bCancel;
	String service;

	public static StartSensingDialog newInstance(Context cxt) {
		StartSensingDialog frag = new StartSensingDialog();
		activity = (Activity) cxt;
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Dialog);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.start_sensing_dialog, container,
				false);
		getDialog().setTitle(R.string.start_dialog_title);

		TextView serviceTitle = (TextView) v.findViewById(R.id.tvService);
		TextView lineTitle = (TextView) v.findViewById(R.id.tvLine);
		TextView originTitle = (TextView) v.findViewById(R.id.tvOrigin);
		TextView destinationTitle = (TextView) v
				.findViewById(R.id.tvDestination);
		serviceTitle.setText(R.string.service);
		lineTitle.setText(R.string.line);
		originTitle.setText(R.string.origin);
		destinationTitle.setText(R.string.destination);

		TextWatcher watcher = new LocalTextWatcher();

		selectOrigin = (AutoCompleteTextView) v.findViewById(R.id.acOrigin);
		selectOrigin.setHint(R.string.autocomplete_origin_hint);
		selectOrigin.setEnabled(false);
		selectOrigin.addTextChangedListener(watcher);

		selectDestination = (AutoCompleteTextView) v
				.findViewById(R.id.acDestination);
		selectDestination.setHint(R.string.autocomplete_destination_hint);
		selectDestination.setEnabled(false);
		selectDestination.addTextChangedListener(watcher);

		selectLine = (AutoCompleteTextView) v.findViewById(R.id.acLine);
		selectLine.setHint(R.string.autocomplete_line_hint);
		selectLine.setEnabled(false);
		selectLine.addTextChangedListener(watcher);
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

		Spinner selectService = (Spinner) v.findViewById(R.id.sService);
		ArrayAdapter<String> adapterService = new ArrayAdapter<String>(
				activity, R.layout.autocomplete_list_item, PTService.SERVICES
						.keySet().toArray(new String[0]));
		selectService.setAdapter(adapterService);
		selectService.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> list, View view, int pos,
					long arg3) {
				service = list.getItemAtPosition(pos).toString();
				serviceLines = PTService.SERVICES.get(service);
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

		bCancel = (Button) v.findViewById(android.R.id.button2);
		bCancel.setText(R.string.alert_dialog_cancel);
		bCancel.setOnClickListener(this);

		bStart = (Button) v.findViewById(android.R.id.button1);
		bStart.setText(R.string.start);
		bStart.setEnabled(false);
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
		switch (v.getId()) {
		case android.R.id.button1:
			Bundle bundle = new Bundle();
			bundle.putString("service", service);
			bundle.putString("line", selectLine.getText().toString());
			bundle.putString("origin", selectOrigin.getText().toString());
			bundle.putString("destination", selectDestination.getText().toString());
			((SensingManager) activity).doPositiveClick(
					SensingManager.DIALOG_START_SENSING, bundle);
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
