package com.cloud2bubble.ptsense;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class Sensing extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment
		
		// setup action bar for tabs
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    Tab tab = actionBar.newTab()
	            .setText(R.string.sensing_tab1)
	            .setTabListener(new MyTabListener<ThisLineFragment>(
	                    this, "this_line", ThisLineFragment.class));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	        .setText(R.string.sensing_tab2)
	        .setTabListener(new MyTabListener<NowFragment>(
	                this, "now", NowFragment.class));
	    actionBar.addTab(tab);
	    
	    actionBar.setDisplayHomeAsUpEnabled(true);
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
}
