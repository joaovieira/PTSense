package com.cloud2bubble.ptsense.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cloud2bubble.ptsense.R;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.util.AttributeSet;
import android.util.Log;

public class MyMultiSelectListPreference extends MultiSelectListPreference {

	public MyMultiSelectListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		Map<String, String> sensorSelectList = getAvailableSensors(context);
		setEntries(sensorSelectList.keySet().toArray(new String[0]));
		setEntryValues(sensorSelectList.values().toArray(new String[0]));
		String defaultValue = getDefaultValue();

		//Log.d("MyMultiSelectListPreference", "settings default value with:" + defaultValue);
		setDefaultValue(defaultValue);
	}

	private String getDefaultValue() {
		CharSequence[] entryVals = getEntryValues();
		return toPersistedPreferenceValue(entryVals);
	}

	private Map<String, String> getAvailableSensors(Context cxt) {
		SensorManager mSensorManager = (SensorManager) cxt
				.getSystemService(Context.SENSOR_SERVICE);

		Map<String, String> sensors = new HashMap<String, String>();

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
			sensors.put("Accelerometer",
					cxt.getString(R.string.sensordata_key_acceleration));

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
			sensors.put("Light", cxt.getString(R.string.sensordata_key_light));

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
			sensors.put("Pressure",
					cxt.getString(R.string.sensordata_key_pressure));

		sensors.put("Microphone", cxt.getString(R.string.sensordata_key_sound));

		if ((LocationManager) cxt.getSystemService(Context.LOCATION_SERVICE) != null)
			sensors.put("GPS", cxt.getString(R.string.gps));

		return sensors;
	}

}
