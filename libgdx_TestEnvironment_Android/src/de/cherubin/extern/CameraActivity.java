package de.cherubin.extern;

import java.io.IOException;
import java.util.List;

import de.cherubin.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
	private Camera camera = null;
	private SurfaceHolder surfaceHolder = null;
	private boolean previewRunning = false;
	private Button btnDone, btnCapture, btnRetake;
	private Bitmap mBitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.camera_screen);
//		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);
//		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder.setFixedSize(getWindow().getWindowManager().getDefaultDisplay().getWidth(), getWindow().getWindowManager()
				.getDefaultDisplay().getHeight());


//		btnRetake.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (previewRunning) {
//					camera.stopPreview();
//				}
//				Camera.Parameters parameters = camera.getParameters();
//				parameters.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
//				camera.setParameters(parameters);
//				try {
//					camera.setPreviewDisplay(surfaceHolder);
//				} catch (IOException e) {
//					Log.d("IOException", e.getMessage());
//				}
//				camera.startPreview();
//				previewRunning = true;
//				btnDone.setVisibility(View.INVISIBLE);
//				btnRetake.setVisibility(View.INVISIBLE);
//				btnCapture.setVisibility(View.VISIBLE);
//			}
//		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		if (previewRunning) {
			camera.stopPreview();
		}
		Camera.Parameters parameters = camera.getParameters();
		List<Size> sizes = parameters.getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, w, h);
		parameters.setPreviewSize(optimalSize.width, optimalSize.height);
		parameters.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);

		setCameraDisplayOrientation(CameraActivity.this, 0, camera);

		camera.setParameters(parameters);
		try {
			camera.setPreviewDisplay(surfaceHolder);
		} catch (IOException e) {
			Log.d("IOException", e.getMessage());
		}
		camera.startPreview();
		previewRunning = true;
	}

	public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		camera = Camera.open(0);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		camera.stopPreview();
		previewRunning = false;
		camera.release();
	}
}