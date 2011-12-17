package tutorial.vortex;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import tutorial.vortex.camera.GenericFunctions;
import tutorial.vortex.filter.MatrixFilter;
import tutorial.vortex.helper.Game;
import tutorial.vortex.helper.Glyphs;
import tutorial.vortex.helper.Mesh.PrimitiveType;
import tutorial.vortex.primitives.Background;
import tutorial.vortex.primitives.Square;
import tutorial.vortex.primitives.Triangle;
import tutorial.vortex.text.Font;
import tutorial.vortex.text.Font.FontStyle;
import tutorial.vortex.text.Font.Text;
import tutorial.vortex.text.TextLabel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

public class VortexRenderer2 implements GLSurfaceView.Renderer {
	private static final String LOG_TAG = VortexRenderer2.class.getSimpleName();

	private static final String TAG = VortexRenderer2.class.getSimpleName();

	private float _xAngle;

	private float _yAngle;

	private Glyphs glyphs;

	private Bitmap bitmap;

	private Canvas canvas;

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

	public VortexRenderer2(GLSurfaceView vortexView) {

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

	private int _width = 320;
	private int _height = 480;
	private long lastFrameStart;
	private float deltaTime;
	private float _aspectRatio;

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		_width = w;
		_height = h;
		_aspectRatio = (float) _width / _height;
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



	/**
	 * Generates a texture from the black and white array filled by the
	 * onPreviewFrame method.
	 */
	void bindCameraTexture(GL10 gl) {
		synchronized (this) {
			if (cameraTexture == null)
				cameraTexture = new int[1];
			else
				gl.glDeleteTextures(1, cameraTexture, 0);

			gl.glGenTextures(1, cameraTexture, 0);
			int tex = cameraTexture[0];
			gl.glBindTexture(GL10.GL_TEXTURE_2D, tex);
			gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_LUMINANCE, 256, 256, 0, GL10.GL_LUMINANCE, GL10.GL_UNSIGNED_BYTE, ByteBuffer.wrap(glCameraFrame));
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		}
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Game.debug("Framerate", Float.toString(Game.fps()));
		// text.setText(Game.getDebugMessage());

		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

//		bindCameraTexture(gl);

		mBackground.draw(gl);

		// gl.glEnable(GL10.GL_DEPTH_TEST);
		// gl.glEnable(GL10.GL_CULL_FACE);
		//
		// gl.glMatrixMode(GL10.GL_PROJECTION);
		// gl.glLoadIdentity();
		//
		// GLU.gluPerspective(gl, 67, _aspectRatio, 1, 1000);
		//
		// gl.glMatrixMode(GL10.GL_MODELVIEW);
		// gl.glLoadIdentity();
		// if (R != null) {
		// float[] temp = new float[16];
		// Matrix.transposeM(temp, 0, R, 0);
		// gl.glMultMatrixf(R, 0);
		// }
		// gl.glPushMatrix();
		// gl.glTranslatef(0.0f, -2.0f, 0.0f);
		// gl.glTranslatef(0.0f, 0.0f, -0.5f);
		// gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		// gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
		// mTriangle.draw(gl);
		// // mSquare.draw(gl);
		// gl.glPopMatrix();
		//
		// gl.glPushMatrix();
		// gl.glTranslatef(3.0f, -2.0f, 0.0f);
		// gl.glTranslatef(0.0f, 0.0f, -0.5f);
		// gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		// gl.glRotatef(60, 0.0f, 1.0f, 0.0f);
		// mTriangle.draw(gl);
		// // mSquare.draw(gl);
		// gl.glPopMatrix();
		//
		// gl.glPushMatrix();
		// gl.glTranslatef(-2.0f, -1.0f, 0.0f);
		// gl.glTranslatef(0.0f, 0.0f, -0.5f);
		// gl.glRotatef(80, 1.0f, 0.0f, 0.0f);
		// gl.glRotatef(60, 0.0f, 1.0f, 0.0f);
		// mTriangle.draw(gl);
		// // mSquare.draw(gl);
		// gl.glPopMatrix();
		//
		// gl.glDisable(GL10.GL_CULL_FACE);
		// gl.glDisable(GL10.GL_DEPTH_TEST);
		//
		// gl.glMatrixMode(GL10.GL_PROJECTION);
		// gl.glLoadIdentity();
		// GLU.gluOrtho2D(gl, 0, _width, 0, _height);
		// gl.glMatrixMode(GL10.GL_MODELVIEW);
		// gl.glLoadIdentity();
		//
		// gl.glEnable(GL10.GL_TEXTURE_2D);
		// gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// gl.glTranslatef(0, _height, 0);
		// text.render();
		// gl.glDisable(GL10.GL_BLEND);
		// gl.glDisable(GL10.GL_TEXTURE_2D);

	}

	/**
	 * sets the mode(either GL10.GL_RGB or GL10.GL_LUMINANCE)
	 * 
	 * @param pMode
	 */
	// public void setMode(int pMode) {
	// switch (pMode) {
	// case GL10.GL_RGB:
	// case GL10.GL_LUMINANCE:
	// this.mode = pMode;
	// break;
	// default:
	// this.mode = GL10.GL_RGB;
	// break;
	// }
	// if (pMode != this.mode)
	// isTextureInitialized = false;
	// }

	// private void initializeTexture(GL10 gl) {
	// byte[] frame;
	// switch (mode) {
	// default:
	// mode = GL10.GL_RGB;
	// case GL10.GL_RGB:
	// frame = new byte[textureSize * textureSize * 3];
	// break;
	// case GL10.GL_LUMINANCE:
	// frame = new byte[textureSize * textureSize];
	// break;
	// }
	// gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, mode, textureSize, textureSize, 0,
	// mode, GL10.GL_UNSIGNED_BYTE, ByteBuffer.wrap(frame));
	// isTextureInitialized = true;
	// }

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

	private int textureSize;

	private int previewFrameHeight;

	private int previewFrameWidth;

	void updateR() {

		SensorManager.getRotationMatrix(temp, I, mAccelerometer, mMagnetic);
		// SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_Y,
		// SensorManager.AXIS_MINUS_X, temp2);
		R = temp;
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
