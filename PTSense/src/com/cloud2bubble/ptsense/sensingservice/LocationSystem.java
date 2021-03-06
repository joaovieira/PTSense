package com.cloud2bubble.ptsense.sensingservice;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class LocationSystem implements LocationListener {

	private static final int FREQUENCY = 1000 * 45;
	private static final int MIN_DISTANCE = 0;

	LocationManager locationManager;

	Location currentBestLocation = null;

	public LocationSystem(Context context) {
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public void start() {
		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				FREQUENCY, MIN_DISTANCE, this);
	}

	public void stop() {
		// Remove the listener you previously added
		locationManager.removeUpdates(this);
	}

	public Location getLocation() {
		return currentBestLocation;
	}

	// Define a listener that responds to location updates
	public void onLocationChanged(Location location) {
		getBestLocation(location);
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		String statusS = "";
		switch (status){
		case LocationProvider.AVAILABLE:
			statusS = "AVAILABLE";
		case LocationProvider.OUT_OF_SERVICE:
			statusS = "OUT_OF_SERVICE";
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			statusS = "TEMPORARILY_UNAVAILABLE";
		}
		Log.d("LocationSystem", "GPS status:" + statusS);
	}

	private void getBestLocation(Location location) {
		if (isBetterLocation(location, currentBestLocation)) {
			Log.d("LocationSystem", "Updated best location with: location="
					+ location.toString());
			currentBestLocation = location;
		}
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}
		return location.getTime() > currentBestLocation.getTime();
	}
}
