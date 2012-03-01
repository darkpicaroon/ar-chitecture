package de.cherubin.extern;

import java.io.IOException;
import java.util.List;

import de.cherubin.helper.RenderMonitor;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * This class handles the camera. In particular, the method setPreviewCallback
 * is used to receive camera images. The camera images are not processed in this
 * class but delivered to the GLLayer. This class itself does not display the
 * camera images.
 * 
 * @author Niels
 * 
 */
public class ARchitectureCamLayer extends SurfaceView implements SurfaceHolder.Callback, PreviewCallback {
	private static final String TAG = "CamLayer";
	Camera mCamera;
	boolean isPreviewRunning = false;
	Camera.PreviewCallback callback;
	private Context mContext;
	private int mDisplayWidth;
	private int mDisplayheight;

	public ARchitectureCamLayer(Context context, Camera.PreviewCallback callback) {
		super(context);
		this.mContext = context;
		this.callback = callback;
		// get Display Size
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		mDisplayWidth = metrics.widthPixels;
		mDisplayheight = metrics.heightPixels;
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		SurfaceHolder mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public ARchitectureCamLayer(Context context) {
		super(context);
		this.mContext = context;
		// get Display Size
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		mDisplayWidth = metrics.widthPixels;
		mDisplayheight = metrics.heightPixels;
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		SurfaceHolder mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// mPreview.setLayoutParams(new LayoutParams(100,100))

	public void surfaceCreated(SurfaceHolder holder) {
		synchronized (this) {
			mCamera = Camera.open();
		}
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

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.d(TAG, "surfaceChanged: " + w + " " + h);
		synchronized (this) {
			if (isPreviewRunning) {
				mCamera.stopPreview();
			}
			Camera.Parameters p = mCamera.getParameters();
			List<Size> sizes = p.getSupportedPreviewSizes();
			Size optimalSize = getOptimalPreviewSize(sizes, w, h);
			p.setPreviewSize(optimalSize.width, optimalSize.height);
			RenderMonitor.mPreviewWidth=optimalSize.width;
			RenderMonitor.mPreviewHeight=optimalSize.height;
			RenderMonitor.mViewAngle = p.getHorizontalViewAngle();
			mCamera.setParameters(p);

			try {
				mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
			mCamera.startPreview();
			isPreviewRunning = true;
		}
	}

	public void onPreviewFrame(byte[] arg0, Camera arg1) {
		if (callback != null)
			callback.onPreviewFrame(arg0, arg1);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		synchronized (this) {
			try {
				if (mCamera != null) {
					mCamera.stopPreview();
					isPreviewRunning = false;
					mCamera.release();
					mCamera = null;
				}
			} catch (Exception e) {
				Log.e("Camera", e.getMessage());
			}
		}
	}
}
