package tutorial.vortex.helper;

import android.util.Log;

public class RenderMonitor {
	public static float aspectRatio = 1;
	// Cam Config
	public final static int mPreviewWidth = 800;
	public final static int mPreviewHeight = 480;
	public final static int mPreviewFactor = 1;

	public static float fps() {
		RenderMonitor.deltaTime();
		return 1 / RenderMonitor.deltaTime;
	}

	public static String getDebugMessage() {
		String s = RenderMonitor.builder.toString();
		RenderMonitor.builder.setLength(0);
		return s;
	}

	public static void debug(String TAG, String text) {
		Log.d(TAG, text);
		RenderMonitor.builder.append(TAG);
		RenderMonitor.builder.append(": ");
		RenderMonitor.builder.append(text);
		RenderMonitor.builder.append("\n");
	}

	public static float deltaTime() {
		long currentFrameStart = java.lang.System.nanoTime();
		RenderMonitor.deltaTime = (currentFrameStart - RenderMonitor.lastFrameStart) / 1000000000.0f;
		RenderMonitor.lastFrameStart = currentFrameStart;
		return RenderMonitor.lastFrameStart;
	}

	public static float deltaTime = 0;
	public static long lastFrameStart = 0;
	public static String debugText = "";
	public static StringBuilder builder = new StringBuilder();
}
