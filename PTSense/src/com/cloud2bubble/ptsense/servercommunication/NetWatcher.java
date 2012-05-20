package com.cloud2bubble.ptsense.servercommunication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetWatcher extends BroadcastReceiver {

	ComponentName netWatcher;

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean connected = false;
		if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
		    NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
		    if(networkInfo.isConnected()) {
		        // Wifi is connected
		        Log.d("NetWatcher", "Wifi is connected: " + String.valueOf(networkInfo));
		        connected = true;
		    }
		} else if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
		    NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		    if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected()) {
		        // Wifi is disconnected
		        Log.d("NetWatcher", "Mobile Data is connected: " + String.valueOf(networkInfo));
		        connected = true;
		    }
		}
		
		if (connected){
			ignoreInternetConnections(context);
			Intent serviceIntent = new Intent(context, C2BClient.class);
			context.startService(serviceIntent);
		}
	}

	private void ignoreInternetConnections(Context context) {
		ComponentName netWatcher = new ComponentName(context, NetWatcher.class);
		context.getPackageManager().setComponentEnabledSetting(netWatcher,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}
}