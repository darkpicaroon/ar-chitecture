package tutorial.vortex.camera;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

import tutorial.vortex.VortexView;

public class CameraPreview {
	private static final String TAG ="CameraPreview";
	// SurfaceHolder mHolder;
	Camera _Camera;
	CameraPreviewHandler _camHandler;

	public CameraPreview(Context context, VortexView _vortexView) {
		Log.d(TAG,"CameraPreview constructor");
		_camHandler = new CameraPreviewHandler(_vortexView);
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		// mHolder = getHolder();
		// mHolder.addCallback(this);
		// mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated() {
		Log.d(TAG,"CameraPreview surfaceCreated");
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		_Camera = Camera.open();
//		_Camera.setPreviewDisplay(holder);
		_Camera.setPreviewCallback(_camHandler);
		_camHandler.init(_Camera);
//		_Camera.release();
//		_Camera = null;
	}

	public void surfaceDestroyed() {
		Log.d(TAG,"CameraPreview surfaceDestroyed");
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		_Camera.stopPreview();
		_Camera.release();
		_Camera = null;
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
		Log.d(TAG,"CameraPreview surfaceChanged");
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = _Camera.getParameters();

		List<Size> sizes = parameters.getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, w, h);
		parameters.setPreviewSize(optimalSize.width, optimalSize.height);

		_Camera.setParameters(parameters);
		_Camera.startPreview();
		
	}
	class Preview extends SurfaceView implements SurfaceHolder.Callback {
	    SurfaceHolder mHolder;
	    Camera mCamera;
	    private int w;
	    private int h;
		private SurfaceHolder mSurfaceHolder;
	    
	    Preview(Context context) {
	        super(context);
	        
	        // Install a SurfaceHolder.Callback so we get notified when the
	        // underlying surface is created and destroyed.
	        mHolder = getHolder();
	        mHolder.addCallback(this);
	        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    }

	    public void surfaceCreated(SurfaceHolder holder) {
	    }

	    public void surfaceDestroyed(SurfaceHolder holder) {
	        // Surface will be destroyed when we return, so stop the preview.
	        // Because the CameraDevice object is not a shared resource, it's very
	        // important to release it when the activity is paused.
	    	CameraPreview.this.surfaceDestroyed();
//	        closeCamera();
	        mSurfaceHolder = null;
	    }

	    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	    	this.w=w;
	    	this.h=h;
	    	mSurfaceHolder = holder;
//	    	if(startPreviewRightAway)
//	    		startPreview();
	    		CameraPreview.this.surfaceCreated();
	    }
	    
	    public int getScreenWidth() {
	    	return w;
	    }
	    
	    public int getScreenHeight() {
	    	return h;
	    }

	}
}
