package com.cloud2bubble.ptsense;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NowFragment extends Fragment {

	private Activity sensingActivity;

	ArrayList<Item> items;
	HeadedListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sensingActivity = getActivity();

		items = new ArrayList<Item>();

		items.add(new SectionItem("smartphone", "Smartphone Sensors"));
		if (SmartphoneSensingService.mAcceleration != null)
			items.add(new EntryItem("oscilation", "Oscilation", "m/s²"));

		if (SmartphoneSensingService.mAmbTemperature != null)
			items.add(new EntryItem("temperature", "Temperature", "ºC"));

		if (SmartphoneSensingService.mLight != null)
			items.add(new EntryItem("light", "Light Intensity", "lux"));

		if (SmartphoneSensingService.mPressure != null)
			items.add(new EntryItem("pressure", "Pressure", "hPa"));

		if (SmartphoneSensingService.mRelHumidity != null)
			items.add(new EntryItem("humidity", "Relative Humidity", "%"));
		
		if (SmartphoneSensingService.soundRecorder != null)
			items.add(new EntryItem("sound", "Sound Level", "dB"));

		items.add(new SectionItem("environment", "Environment Sensors"));
		
		// TODO change with external environment sensors connected
		if (true)
			items.add(new EmptyItem("env_sensors", "No environment sensors connected"));
		
		items.add(new SectionItem("body", "Body Sensors"));
		
		if (true)
			items.add(new EmptyItem("body_sensors", "No body sensors connected"));

		adapter = new HeadedListAdapter(sensingActivity, items);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.now_fragment, container, false);
		
		ListView list = (ListView) v.findViewById(R.id.lvSensingNow);

		list.setAdapter(adapter);
		
		return v;
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			updateUI(intent);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		sensingActivity.registerReceiver(broadcastReceiver, new IntentFilter(
				SmartphoneSensingService.BROADCAST_ACTION));
	}

	@Override
	public void onPause() {
		super.onPause();
		sensingActivity.unregisterReceiver(broadcastReceiver);
	}

	private void updateUI(Intent intent) {

		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			Item element = iterator.next();
			if (element.getType() == Item.ENTRY)
				((EntryItem) element).value = intent.getStringExtra(element
						.getKey());
		}

		adapter.notifyDataSetChanged();
	}
}
