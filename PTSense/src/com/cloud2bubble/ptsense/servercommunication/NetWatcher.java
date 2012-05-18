package com.cloud2bubble.ptsense.servercommunication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWatcher extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			if (info.isConnected()) {
				// start service
				Intent serviceIntent = new Intent(context, C2BClient.class);
				context.startService(serviceIntent);
			} else {
				// stop service
				Intent serviceIntent = new Intent(context, C2BClient.class);
				context.stopService(serviceIntent);
			}
		}
	}
}