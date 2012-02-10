package de.cherubin;

import tutorial.vortex.filter.MatrixFilter;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.test.meshcolortexture.TestEnvironment;
import com.test.meshcolortexture.TestEnvironmentRenderer;

import de.cherubin.extern.ARchitectureCamLayer;
import de.cherubin.extern.MyAndroidApplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class TestEnvironment_Android extends MyAndroidApplication implements SensorEventListener {
	private ARchitectureCamLayer mPreview;
	private SensorManager _sensorManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useCompass = true;
		config.useWakelock = false;
		_sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//		_app=new TestEnvironment();
		initialize(new TestEnvironmentRenderer(), config);
	}

	protected void onResume() {
		super.onResume();
		_sensorManager.registerListener(this, _sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
		_sensorManager.registerListener(this, _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
	}

	protected void onPause() {
		super.onPause();
		_sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			updateSensorData(event.values.clone(), event.sensor.getType());
//			_app.updateR(R);
		}
	}

	float[] mAccelerometer = new float[] { 0, 0, 0 };
	float[] mMagnetic = new float[] { 0, 0, 0 };

	public void updateSensorData(float[] values, int type) {
		if (type == Sensor.TYPE_ACCELEROMETER) {
			mAccelerometer = MatrixFilter.LOWPASS.filter(values, mAccelerometer);
			updateR();
		}
		if (type == Sensor.TYPE_MAGNETIC_FIELD) {
			mMagnetic = MatrixFilter.LOWPASS.filter(values, mMagnetic);
			updateR();
		}
	}

	private float[] R = new float[16];
	float[] I = new float[16];
	float[] temp = new float[16];
	float[] temp2 = new float[16];

	void updateR() {
		SensorManager.getRotationMatrix(temp, I, mAccelerometer, mMagnetic);
		SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, temp2);
		R = temp2;
	}

}
