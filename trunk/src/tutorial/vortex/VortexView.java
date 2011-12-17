package tutorial.vortex;

import java.nio.ByteBuffer;

import android.content.Context;
import android.hardware.Sensor;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class VortexView extends GLSurfaceView {
	private static final String LOG_TAG = VortexView.class.getSimpleName();
	private VortexRenderer _renderer;

	public VortexView(Context context) {
		super(context);
		_renderer = new VortexRenderer(this);
		setRenderer(_renderer);
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

	public void updateBackground(ByteBuffer frameBuffer) {
		_renderer.setBackground(frameBuffer);
	}

	public void setPreviewFrameSize(int textureSize, int previewFrameWidth, int previewFrameHeight) {
		_renderer.setPreviewFrameSize(textureSize, previewFrameWidth, previewFrameHeight);
	}
}
