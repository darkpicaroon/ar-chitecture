package tutorial.vortex.camera;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import tutorial.vortex.VortexView;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;

public class CameraPreviewHandler implements PreviewCallback {
	private static final String TAG = "CameraPreviewHandler";
	private VortexView _glView;
	private ByteBuffer frameBuffer;
	private int previewFrameWidth;
	private int previewFrameHeight;
	private int textureSize;
	private int bwSize;
	private byte[] frame;
	public final static int MODE_RGB = 0;
	public final static int MODE_GRAY = 1;

	public CameraPreviewHandler(VortexView glSurfaceView) {
		this._glView = glSurfaceView;
		Log.d(TAG, "CameraPreview constructor");
	}

	/**
	 * the size of the camera preview frame is dynamic we will calculate the
	 * next power of two texture size in which the preview frame will fit and
	 * set the corresponding size in the renderer how to decode camera YUV to
	 * RGB for opengl:
	 * http://groups.google.de/group/android-developers/browse_thread
	 * /thread/c85e829ab209ceea
	 * /d3b29d3ddc8abf9b?lnk=gst&q=YUV+420#d3b29d3ddc8abf9b
	 * 
	 * @param camera
	 */
	protected void init(Camera camera) {
		Log.d(TAG, "CameraPreview init");
		Parameters camParams = camera.getParameters();
		// check if the pixel format is supported
		// if (camParams.getPreviewFormat() == PixelFormat.YCbCr_420_SP) {
		// setMode(MODE_RGB);
		// } else if (camParams.getPreviewFormat() == PixelFormat.YCbCr_422_SP)
		// {
		// setMode(MODE_GRAY);
		// } else {
		// Das Format ist semi planar, Erklärung:
		// semi-planar YCbCr 4:2:2 : two arrays, one with all Ys, one with
		// Cb and Cr.
		// Quelle:
		// http://www.celinuxforum.org/CelfPubWiki/AudioVideoGraphicsSpec_R2
		// throw new
		// AndARRuntimeException(res.getString(R.string.error_unkown_pixel_format));
		// }
		// get width/height of the camera
		Size previewSize = camParams.getPreviewSize();
		previewFrameWidth = previewSize.width;
		previewFrameHeight = previewSize.height;
		textureSize = GenericFunctions.nextPowerOfTwo(Math.max(previewFrameWidth, previewFrameHeight));
		// frame = new byte[textureSize * textureSize * 3];
		bwSize = previewFrameWidth * previewFrameHeight;
		frame = new byte[bwSize * 3];

		frameBuffer = GraphicsUtil.makeByteBuffer(frame.length);
		_glView.setPreviewFrameSize(textureSize, previewFrameWidth, previewFrameHeight);
		// markerInfo.setImageSize(previewFrameWidth, previewFrameHeight);
		// threadsRunning = true;
		// if (Config.USE_ONE_SHOT_PREVIEW) {
		// constFPS = new CameraConstFPS(5, camera);
		// constFPS.start();
		// }
		// if (focusHandler == null) {
		// focusHandler = new AutoFocusHandler(camera);
		// focusHandler.start();
		// markerInfo.setVisListener(focusHandler);
		// }

	}

	protected void setMode(int pMode) {
		// synchronized (modeLock) {
		// this.mode = pMode;
		// switch (mode) {
		// case MODE_RGB:
		// frameSink.setMode(GL10.GL_RGB);
		// break;
		// case MODE_GRAY:
		// frameSink.setMode(GL10.GL_LUMINANCE);
		// break;
		// }
		// }
	}

	@Override
	public synchronized void onPreviewFrame(byte[] data, Camera camera) {
		Log.d(TAG, "CameraPreview onPreviewFrame");
		bwSize = previewFrameWidth * previewFrameHeight;
		frame = new byte[bwSize * 3];
		frameBuffer.position(0);
		frameBuffer.put(frame);
		frameBuffer.position(0);
		_glView.updateBackground(frameBuffer);
	}

}
