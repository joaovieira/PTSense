package com.cloud2bubble.ptsense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class NowFragment extends ListFragment {

	private Activity sensingActivity;

	// remove
	LinearLayout sensingNowEnvironment;
	TextView tvLight, tvAccelX, tvAccelY, tvAccelZ, tvPressure, tvHumidity,
			tvTemperature;

	SimpleAdapter ad;
	List<Map<String, String>> l;

	ArrayList<Item> items;
	HeadedListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sensingActivity = getActivity();

		items = new ArrayList<Item>();

		items.add(new SectionItem("environment", "Environment"));
		items.add(new EntryItem("accelX", "Item 1", "This is item 1.1"));
		items.add(new EntryItem("accelY", "Item 2", "This is item 1.2"));
		items.add(new EntryItem("accelZ", "Item 3", "This is item 1.3"));

		items.add(new SectionItem("personal", "Category 2"));
		items.add(new EntryItem("light", "Item 4", "This is item 2.1"));
		adapter = new HeadedListAdapter(sensingActivity, items);

		setListAdapter(adapter);
	}

	/*
	 * public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 * Bundle savedInstanceState) { // Inflate the layout for this fragment
	 * //ListView v = (ListView) inflater.inflate(R.layout.now_fragment,
	 * container, false);
	 * 
	 * ArrayList<Item> items = new ArrayList<Item>();
	 * 
	 * items.add(new SectionItem("Category 1")); items.add(new
	 * EntryItem("Item 1", "This is item 1.1")); items.add(new
	 * EntryItem("Item 2", "This is item 1.2")); items.add(new
	 * EntryItem("Item 3", "This is item 1.3"));
	 * 
	 * items.add(new SectionItem("Category 2")); items.add(new
	 * EntryItem("Item 4", "This is item 2.1")); items.add(new
	 * EntryItem("Item 5", "This is item 2.2")); items.add(new
	 * EntryItem("Item 6", "This is item 2.3")); items.add(new
	 * EntryItem("Item 7", "This is item 2.4")); HeadedListAdapter adapter = new
	 * HeadedListAdapter(sensingActivity, items);
	 * 
	 * setListAdapter(adapter);
	 * 
	 * /*l = getExampleList();
	 * 
	 * String[] from = { "ExampleId", "ExampleName" }; int[] to = {
	 * android.R.id.text1, android.R.id.text2 };
	 * 
	 * ad = new SimpleAdapter(sensingActivity, l,
	 * android.R.layout.simple_list_item_2, from, to); ListView lv = (ListView)
	 * v.findViewById(R.id.list); lv.setAdapter(ad);
	 * 
	 * return v; }
	 */
	/**
	 * Fill a list object
	 * 
	 * @return List<String>
	 */
	/*
	 * private List<Map<String, String>> getExampleList() { List<Map<String,
	 * String>> l = new ArrayList<Map<String, String>>(); for (int i = 0; i <
	 * 10; i++) { Map<String, String> m = new HashMap<String, String>();
	 * m.put("ExampleId", "Other Value " + String.format("%c", i + 65));
	 * m.put("ExampleName", "Something else " + i); l.add(m); } return l; }
	 */
	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { // Inflate the layout for this
	 * fragment // ListView v = (ListView)
	 * inflater.inflate(R.layout.now_fragment, // container, false);
	 * 
	 * sensingActivity = getActivity();
	 * 
	 * String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
	 * "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2"
	 * }; setListAdapter(new ArrayAdapter<String>(sensingActivity,
	 * android.R.layout.simple_list_item_1, values)); /* sensingNowEnvironment =
	 * (LinearLayout) v.findViewById(R.id.llSensingNow);
	 * 
	 * if (SmartphoneSensingService.mAcceleration != null) { tvAccelX = new
	 * TextView(sensingActivity); sensingNowEnvironment.addView(tvAccelX);
	 * tvAccelY = new TextView(sensingActivity);
	 * sensingNowEnvironment.addView(tvAccelY); tvAccelZ = new
	 * TextView(sensingActivity); sensingNowEnvironment.addView(tvAccelZ); }
	 * 
	 * if (SmartphoneSensingService.mAmbTemperature != null) { tvTemperature =
	 * new TextView(sensingActivity);
	 * sensingNowEnvironment.addView(tvTemperature); }
	 * 
	 * if (SmartphoneSensingService.mLight != null) { tvLight = new
	 * TextView(sensingActivity); sensingNowEnvironment.addView(tvLight); }
	 * 
	 * if (SmartphoneSensingService.mPressure != null) { tvPressure = new
	 * TextView(sensingActivity); sensingNowEnvironment.addView(tvPressure); }
	 * 
	 * if (SmartphoneSensingService.mRelHumidity != null) { tvHumidity = new
	 * TextView(sensingActivity); sensingNowEnvironment.addView(tvHumidity); }
	 * 
	 * 
	 * // return v; }
	 */

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
			if (element instanceof EntryItem)
				((EntryItem) element).value = intent.getStringExtra(element.getKey());
		}

		adapter.notifyDataSetChanged();

		/*
		 * String sensedData = ""; if((sensedData =
		 * intent.getStringExtra("accelX")) != null)
		 * tvAccelX.setText(sensedData);
		 * 
		 * if((sensedData = intent.getStringExtra("accelY")) != null)
		 * tvAccelY.setText(sensedData);
		 * 
		 * if((sensedData = intent.getStringExtra("accelZ")) != null)
		 * tvAccelZ.setText(sensedData);
		 * 
		 * if((sensedData = intent.getStringExtra("temperature")) != null)
		 * tvTemperature.setText(sensedData);
		 * 
		 * if((sensedData = intent.getStringExtra("light")) != null)
		 * tvLight.setText(sensedData);
		 * 
		 * if((sensedData = intent.getStringExtra("pressure")) != null)
		 * tvPressure.setText(sensedData);
		 * 
		 * if((sensedData = intent.getStringExtra("humidity")) != null)
		 * tvHumidity.setText(sensedData);
		 */
	}
}
