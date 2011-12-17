package tutorial.vortex;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import tutorial.vortex.filter.MatrixFilter;
import tutorial.vortex.helper.Glyphs;
import tutorial.vortex.helper.RenderMonitor;
import tutorial.vortex.primitives.Background;
import tutorial.vortex.primitives.Square;
import tutorial.vortex.primitives.Triangle;
import tutorial.vortex.text.Font;
import tutorial.vortex.text.Font.FontStyle;
import tutorial.vortex.text.Font.Text;
import tutorial.vortex.text.TextLabel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

public class ARchitectureRenderer implements GLSurfaceView.Renderer {

	private static final String TAG = ARchitectureRenderer.class.getSimpleName();

	private float _xAngle;

	private float _yAngle;

	private Glyphs glyphs;

	private Bitmap bitmap;

	private Triangle mTriangle;

	private Square mSquare;
	private Background mBackground;

	private Context mContext;

	private TextLabel label;

	private Font font;

	private Text text;

	private float size;
	int onDrawFrameCounter = 1;
	int[] cameraTexture;
	byte[] glCameraFrame = new byte[256 * 256]; // size of a texture must be a
												// power of 2
	FloatBuffer cubeBuff;
	FloatBuffer texBuff;

	public ARchitectureRenderer(GLSurfaceView vortexView) {

		this.mContext = vortexView.getContext();

		// Create an empty, mutable bitmap
		// bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		// get a canvas to paint over the bitmap
		// canvas = new Canvas(bitmap);
		// this.glyphs = new Glyphs(bitmap);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		mSquare = new Square();
		mTriangle = new Triangle(gl, mContext);
		mBackground = new Background(gl);
		font = new Font(gl, mContext.getAssets(), "seven.ttf", 12, FontStyle.Plain);
		text = font.newText(gl);
		text.setText("This is a test string!!");
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		gl.glClearColor(0, 0, 0, 0);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}

	private int _width = 480;
	private int _height = 320;
	private long lastFrameStart;
	private float deltaTime;

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		Log.d(TAG, "onSurfaceChanged");
		_width = w;
		_height = h;
		RenderMonitor.aspectRatio = (float) _width / _height;
		gl.glViewport(0, 0, w, h);
	}

	public void setXAngle(float angle) {
		_xAngle = angle;
	}

	public float getXAngle() {
		return _xAngle;
	}

	public void setYAngle(float angle) {
		_yAngle = angle;
	}

	public float getYAngle() {
		return _yAngle;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		RenderMonitor.debug("Framerate", Float.toString(RenderMonitor.fps()));
		text.setText(RenderMonitor.getDebugMessage());

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		GLU.gluPerspective(gl, 67, RenderMonitor.aspectRatio, 1, 1000);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		if (R != null) {
			float[] temp = new float[16];
			Matrix.transposeM(temp, 0, R, 0);
			gl.glMultMatrixf(R, 0);
		}
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -2.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -0.5f);
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
		mTriangle.draw(gl);
		// mSquare.draw(gl);
		gl.glPopMatrix();
		//
		gl.glPushMatrix();
		gl.glTranslatef(3.0f, -2.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -0.5f);
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(60, 0.0f, 1.0f, 0.0f);
		mTriangle.draw(gl);
		// mSquare.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(-2.0f, -1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -0.5f);
		gl.glRotatef(80, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(60, 0.0f, 1.0f, 0.0f);
		mTriangle.draw(gl);
		// mSquare.draw(gl);
		gl.glPopMatrix();
		//
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, _width, 0, _height);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glTranslatef(0, _height, 0);
//		gl.glRotatef(90, 0.0f, 0.0f, 1.0f);
		
//		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		text.render();
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);

	}

	private float _angle;

	public void setAngle(float angle) {
		_angle = angle;
	}

	float[] mAccelerometer = new float[] { 0, 0, 0 };
	float[] mMagnetic = new float[] { 0, 0, 0 };

	public void updateMagnetic(float[] values) {
		mMagnetic = MatrixFilter.LOWPASS.filter(values, mMagnetic);
		// Log.d(TAG, String.format("updateMagnetic: %f,%f,%f", values[0],
		// values[1], values[2]));
		updateR();
	}

	public void updateAccelerometer(float[] values) {
		mAccelerometer = MatrixFilter.LOWPASS.filter(values, mAccelerometer);
		updateR();
		// Log.d(TAG,String.format("updateAccelerometer: %f,%f,%f",values[0]
		// ,values[1],values[2]));
	}

	private float[] R = new float[16];
	float[] I = new float[16];
	float[] temp = new float[16];
	float[] temp2 = new float[16];

	void updateR() {

		SensorManager.getRotationMatrix(temp, I, mAccelerometer, mMagnetic);
		SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, temp2);
		R = temp2;
		// text.setText("R gültig");
		// } else {
		// text.setText("R ungültig");
		// }
	}

	public byte[] getFrameBuffer() {
		return mBackground.getFrameBuffer();
	}

	public void setFrameBuffer(byte[] frame) {
		mBackground.setFrameBuffer(frame);
	}
}
