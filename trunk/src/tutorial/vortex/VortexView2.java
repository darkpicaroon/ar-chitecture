package tutorial.vortex;

import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class VortexView2 extends GLSurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

	private static final String LOG_TAG = VortexView2.class.getSimpleName();
	private static final String TAG = "VortexView";
	private VortexRenderer2 _renderer;
	byte[] glCameraFrame = new byte[256 * 256];

	public VortexView2(Context context) {
		super(context);
		_renderer = new VortexRenderer2(this);
//		this.setEGLConfigChooser(5, 6, 5, 8, 16, 0);
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		setRenderer(_renderer);
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	private float _x = 0;
	private float _y = 0;

	public boolean onTouchEvent(final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			_x = event.getX();
			_y = event.getY();
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			final float xdiff = (_x - event.getX());
			final float ydiff = (_y - event.getY());
			queueEvent(new Runnable() {
				public void run() {
					_renderer.setXAngle(_renderer.getXAngle() + ydiff);
					_renderer.setYAngle(_renderer.getYAngle() + xdiff);
				}
			});
			_x = event.getX();
			_y = event.getY();
		}
		return true;
	}

	public void updateSensorData(float[] values, int type) {
		if (type == Sensor.TYPE_ACCELEROMETER) {
			_renderer.updateAccelerometer(values);
		}
		if (type == Sensor.TYPE_MAGNETIC_FIELD) {
			_renderer.updateMagnetic(values);
		}
	}

	/**
	 * This method is called if a new image from the camera arrived. The camera
	 * delivers images in a yuv color format. It is converted to a black and
	 * white image with a size of 256x256 pixels (only a fraction of the
	 * resulting image is used). Afterwards Rendering the frame (in the main
	 * loop thread) is started by setting the newFrameLock to true.
	 */
	public void onPreviewFrame(byte[] yuvs, Camera camera) {
//		Log.d(TAG,"onPreviewFrame");
		int bwCounter = 0;
		int yuvsCounter = 0;
		for (int y = 0; y < 160; y++) {
			System.arraycopy(yuvs, yuvsCounter, glCameraFrame, bwCounter, 240);
			_renderer.setFrameBuffer(glCameraFrame);
			yuvsCounter = yuvsCounter + 240;
			bwCounter = bwCounter + 256;
		}
	}
}
