package com.cloud2bubble.ptsense.sensingservice;

import java.util.concurrent.ArrayBlockingQueue;

public class DataProcessor {

	public static Float processAccelerometerData(
			ArrayBlockingQueue<Float> accelerationsX,
			ArrayBlockingQueue<Float> accelerationsY,
			ArrayBlockingQueue<Float> accelerationsZ) {
		// TODO Auto-generated method stub
		return new Float(0.0);
	}

	public static Float processTemperatureData(
			ArrayBlockingQueue<Float> ambTemperatureValues) {
		// TODO Auto-generated method stub
		return new Float(0.0);
	}

	public static Float processLightData(
			ArrayBlockingQueue<Float> ambTemperatureValues) {
		// TODO Auto-generated method stub
		return new Float(0.0);
	}

	public static Float processPressureData(
			ArrayBlockingQueue<Float> ambTemperatureValues) {
		// TODO Auto-generated method stub
		return new Float(0.0);
	}

	public static Float processHumidityData(
			ArrayBlockingQueue<Float> ambTemperatureValues) {
		// TODO Auto-generated method stub
		return new Float(0.0);
	}

	public static Float processSoundData(
			ArrayBlockingQueue<Float> ambTemperatureValues) {
		// TODO Auto-generated method stub
		return new Float(0.0);
	}

}
