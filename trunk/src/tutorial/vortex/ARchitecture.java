package tutorial.vortex;

import java.io.IOException;

import tutorial.vortex.camera.CamLayer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

public class ARchitecture extends Activity implements SensorEventListener {
	private CamLayer mPreview;
	private static final String LOG_TAG = ARchitecture.class.getSimpleName();
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
		mPreview = new CamLayer(this, _vortexView);
		
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

	// class Preview extends SurfaceView implements SurfaceHolder.Callback {
	// SurfaceHolder mHolder;
	// Camera mCamera;
	// private int w;
	// private int h;
	//
	// Preview(Context context) {
	// super(context);
	//
	// // Install a SurfaceHolder.Callback so we get notified when the
	// // underlying surface is created and destroyed.
	// mHolder = getHolder();
	// mHolder.addCallback(this);
	// mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	// }
	//
	// public void surfaceCreated(SurfaceHolder holder) {
	// }
	//
	// public void surfaceDestroyed(SurfaceHolder holder) {
	// // Surface will be destroyed when we return, so stop the preview.
	// // Because the CameraDevice object is not a shared resource, it's
	// // very
	// // important to release it when the activity is paused.
	// stopPreview();
	// closeCamera();
	// mSurfaceHolder = null;
	// }
	//
	// public void surfaceChanged(SurfaceHolder holder, int format, int w, int
	// h) {
	// this.w = w;
	// this.h = h;
	// mSurfaceHolder = holder;
	// if (startPreviewRightAway)
	// startPreview();
	// }
	//
	// public int getScreenWidth() {
	// return w;
	// }
	//
	// public int getScreenHeight() {
	// return h;
	// }
	//
	// }
	//
	// /**
	// * Close the camera and stop detecting markers.
	// */
	// private void stopPreview() {
	// if (camera != null && camStatus.previewing) {
	// camStatus.previewing = false;
	// camera.stopPreview();
	// }
	//
	// }
	//
	// private void closeCamera() {
	// if (camera != null) {
	// CameraHolder.instance().keep();
	// CameraHolder.instance().release();
	// camera = null;
	// camStatus.previewing = false;
	// }
	// }
}