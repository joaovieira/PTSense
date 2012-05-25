package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.R;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	static final String PREFS_NAME = "defaults";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		getPrefs(this);
        getPreferenceManager().setSharedPreferencesName(PREFS_NAME);
		addPreferencesFromResource(R.xml.settings);
		
		if (getIntent().getBooleanExtra("open_devices", false) == true){
			int pos = findPreference("devices").getOrder();

			// simulate a click / call it!!
			getPreferenceScreen().onItemClick( null, null, pos+1, 0 ); 
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent homeIntent = new Intent(this, Home.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override 
    protected void onResume(){
        super.onResume();
		ListPreference listPref = (ListPreference) findPreference("notifications"); 
		listPref.setSummary(listPref.getEntry());
		
        // Set up a listener whenever a key changes             
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override 
    protected void onPause() { 
        super.onPause();
        // Unregister the listener whenever a key changes             
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);     
    } 
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	    Preference pref = findPreference(key);

	    if (pref instanceof ListPreference) {
	        ListPreference listPref = (ListPreference) pref;
	        pref.setSummary(listPref.getEntry());
	    }
	}
	
	public static SharedPreferences getPrefs(Context context){
		PreferenceManager.setDefaultValues(context, PREFS_NAME, MODE_PRIVATE, R.xml.settings, true);
		return context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	}
}
