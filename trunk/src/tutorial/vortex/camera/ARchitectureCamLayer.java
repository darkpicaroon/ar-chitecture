package tutorial.vortex.camera;

import java.io.IOException;
import java.util.List;

import tutorial.vortex.helper.RenderMonitor;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
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
		//get Display Size
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
			mCamera=Camera.open();
			Camera.Parameters p = mCamera.getParameters();
			p.setPreviewSize(RenderMonitor.mPreviewWidth/RenderMonitor.mPreviewFactor, RenderMonitor.mPreviewHeight/RenderMonitor.mPreviewFactor);
			mCamera.setParameters(p);
			mCamera.startPreview();
			try {
				mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {
				Log.e(TAG, "mCamera.setPreviewDisplay(holder);");
			}
			// mCamera.setPreviewCallback(this);
		}
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
					mCamera=null;
				}
			} catch (Exception e) {
				Log.e("Camera", e.getMessage());
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		synchronized (this) {
//			this.mPreviewWidth = w;
//			this.mPreviewHeight = h;
			Log.d(TAG, "surfaceChanged: "+w+" "+h);
		}
	}

	public void onPreviewFrame(byte[] arg0, Camera arg1) {
		if (callback != null)
			callback.onPreviewFrame(arg0, arg1);
	}
}
