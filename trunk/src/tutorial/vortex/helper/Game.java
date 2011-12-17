package tutorial.vortex.helper;

import android.util.Log;

public class Game {
	private static float deltaTime=0;
	private static long lastFrameStart=0;
	public static String debugText ="";
	private static StringBuilder builder= new StringBuilder();

	public static float fps() {
		deltaTime();
		return 1/deltaTime;
	}
	public static float deltaTime() {
		long currentFrameStart = java.lang.System.nanoTime();
		deltaTime = (currentFrameStart - lastFrameStart) / 1000000000.0f;
		lastFrameStart = currentFrameStart;
		return lastFrameStart;
	}
	
	public static void debug(String TAG, String text){
		Log.d(TAG,text);
		builder.append(TAG);
		builder.append(": ");
		builder.append(text);
		builder.append("\n");
	}
	public static String getDebugMessage(){
		String s = builder.toString();
		builder.setLength(0);
		return s;
	}
}
