package com.cloud2bubble.ptsense.component;

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
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;

public class MyMultiSelectListPreference extends MultiSelectListPreference {

	Map<String, String> sensors = new HashMap<String, String>();

	public MyMultiSelectListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		Map<String, String> sensorSelectList = getAvailableSensors(context);
		setEntries(sensorSelectList.keySet().toArray(new String[0]));
		setEntryValues(sensorSelectList.values().toArray(new String[0]));
		Set<String> defaultValue = getDefaultValueSet();
		setDefaultValue(defaultValue);
	}

	private Set<String> getDefaultValueSet() {
		Set<String> defaultValue = new HashSet<String>();
		Iterator<Entry<String, String>> it = sensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
	        defaultValue.add(pairs.getValue());
	    }
	    return defaultValue;
	}

	private Map<String, String> getAvailableSensors(Context cxt) {
		// Get the SensorManager
		SensorManager mSensorManager = (SensorManager) cxt
				.getSystemService(Context.SENSOR_SERVICE);

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
			sensors.put("Accelerometer",
					cxt.getString(R.string.sensordata_key_acceleration));

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
			sensors.put("Ambient Temperature",
					cxt.getString(R.string.sensordata_key_temperature));

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
			sensors.put("Light", cxt.getString(R.string.sensordata_key_light));

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
			sensors.put("Pressure",
					cxt.getString(R.string.sensordata_key_pressure));

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
			sensors.put("Relative Humidity",
					cxt.getString(R.string.sensordata_key_humidity));

		sensors.put("Microphone", cxt.getString(R.string.sensordata_key_sound));

		if ((LocationManager) cxt.getSystemService(Context.LOCATION_SERVICE) != null)
			sensors.put("GPS", cxt.getString(R.string.gps));

		return sensors;
	}
	
}
