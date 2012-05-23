package com.cloud2bubble.ptsense.sensingservice;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.util.Pair;

public class DataProcessor {

	private static final double AREF_CONSTANT = Math.pow(1, -5);

	public static Float processAccelerometerData(List<Float> tmpAccelerationsX,
			List<Float> tmpAccelerationsY, List<Float> tmpAccelerationsZ) {
		int size = getSmallestArray(tmpAccelerationsX, tmpAccelerationsY,
				tmpAccelerationsZ);

		List<Float> comfortIndexes = new ArrayList<Float>();
		for (int i = 0; i < size; i++) {
			Double accelT = getAccelerationT(tmpAccelerationsX.get(i),
					tmpAccelerationsY.get(i), tmpAccelerationsZ.get(i));
			Double comfortIndex = normalize(accelT);
			comfortIndexes.add(comfortIndex.floatValue());
		}

		return getAverage(comfortIndexes);
	}

	private static Double normalize(Double accelT) {
		Double log = Math.log(accelT / AREF_CONSTANT);
		return 20 * log;
	}

	// formula as ISO standard 2631
	private static Double getAccelerationT(Float ax, Float ay, Float az) {
		Double ax2 = Math.pow(1.4 * ax, 2);
		Double ay2 = Math.pow(1.4 * ay, 2);
		Double az2 = Math.pow(az, 2);
		Double aLevel = Math.sqrt(ax2 + ay2 + az2);
		return aLevel;
	}

	private static int getSmallestArray(List<Float> tmpAccelerationsX,
			List<Float> tmpAccelerationsY, List<Float> tmpAccelerationsZ) {
		int smallest = Math.max(tmpAccelerationsX.size(),
				tmpAccelerationsY.size());
		smallest = Math.max(smallest, tmpAccelerationsZ.size());
		return smallest;
	}

	public static Float processTemperatureData(
			List<Float> tmpAmbTemperatureValues) {
		return getAverage(tmpAmbTemperatureValues);
	}

	public static Float processLightData(List<Float> tmpLightValues) {
		return getAverage(tmpLightValues);
	}

	public static Float processPressureData(List<Float> tmpPressureValues) {
		return getAverage(tmpPressureValues);
	}

	public static Float processHumidityData(List<Float> tmpRelHumidityValues) {
		return getAverage(tmpRelHumidityValues);
	}

	public static Float processSoundData(List<Double> tmpSoundValues) {
		return getAverageDouble(tmpSoundValues).floatValue();
	}

	private static Float getAverage(List<Float> list) {
		Float count = 0.0f;
		for (int i = 0; i < list.size(); i++)
			count += list.get(i);
		return count / list.size();
	}

	private static Double getAverageDouble(List<Double> list) {
		Double count = 0.0;
		for (int i = 0; i < list.size(); i++)
			count += list.get(i);
		return count / list.size();
	}

	public static Pair<Float, Float> processCoordinate(Location location) {
		if (location == null)
			return new Pair<Float, Float>(Float.NaN, Float.NaN);
		else
			return new Pair<Float, Float>((float) location.getLatitude(),
					(float) location.getLongitude());
	}

}
