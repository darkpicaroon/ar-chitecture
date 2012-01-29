package tutorial.vortex;

import tutorial.vortex.camera.ARchitectureCamLayer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

public class ARchitecture extends Activity implements SensorEventListener {
	private ARchitectureCamLayer mPreview;
	private static final String TAG = ARchitecture.class.getSimpleName();
	private ARchitectureView _vortexView;
	private SensorManager _sensorManager;
	static int counter=0;
	
	// private tutorial.vortex.Vortex.Preview previewSurface;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		disableScreenTurnOff();
		setNoTitle();
		setFullscreen();
		_vortexView = new ARchitectureView(this);
		_sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//		mPreview = new ARchitectureCamLayer(this, _vortexView);
		mPreview = new ARchitectureCamLayer(this);
		
//        if (counter==2) {
//        	MediaPlayer mp=MediaPlayer.create(this, R.raw);
//        	mp.start();
//        }
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(_vortexView);
		addContentView(mPreview, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// previewSurface = new Preview(this);

	}
    /**
     * Avoid that the screen get's turned off by the system.
     */
	public void disableScreenTurnOff() {
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    /**
     * Maximize the application.
     */
    public void setFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
   
    public void setNoTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    } 
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		synchronized (this) {
			_vortexView.updateSensorData(sensorEvent.values.clone(), sensorEvent.sensor.getType());
		}
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

}