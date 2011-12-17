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
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

public class VortexRenderer implements GLSurfaceView.Renderer {
	private static final String LOG_TAG = VortexRenderer.class.getSimpleName();

	private static final String TAG = VortexRenderer.class.getSimpleName();

	private float _xAngle;

	private float _yAngle;

	private Glyphs glyphs;

	private Bitmap bitmap;

	private Canvas canvas;

	private Triangle mTriangle;

	private Square mSquare;

	private Context mContext;

	private TextLabel label;

	private Font font;

	private Text text;

	private float size;

	public VortexRenderer(VortexView vortexView) {

		this.mContext = vortexView.getContext();

		mSquare = new Square();
		// label = new TextLabel();
		// label.setBitmapFont(BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.glyphs_green));
		// Create an empty, mutable bitmap
		// bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		// get a canvas to paint over the bitmap
		// canvas = new Canvas(bitmap);
		// this.glyphs = new Glyphs(bitmap);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		font = new Font(gl, mContext.getAssets(), "seven.ttf", 12, FontStyle.Plain);
		text = font.newText(gl);
		text.setText("This is a test string!!");
		// mSquare.loadGLTexture(gl, this.mContext);
		// gl.glEnable(GL10.GL_TEXTURE_2D);
		// Enable Texture Mapping ( NEW )
		// gl.glShadeModel(GL10.GL_SMOOTH);
		// preparation
		// gl.glMatrixMode(GL10.GL_PROJECTION);
		// size = .01f * (float) Math.tan(Math.toRadians(60.0) / 2);
		// float ratio = _width / _height;
		// perspective:
		// gl.glFrustumf(-size, size, -size / ratio, size / ratio, 0.01f,
		// 100.0f);
		// orthographic:
		// gl.glOrthof(-1, 1, -1 / ratio, 1 / ratio, 0.01f, 100.0f);
		// gl.glViewport(0, 0, (int) _width, (int) _height);
		// gl.glMatrixMode(GL10.GL_MODELVIEW);

		// define the color we want to be displayed as the "clipping wall"
		gl.glClearColor(0f, 0f, 0f, 1.0f);
		// enable the differentiation of which side may be visible
		// gl.glEnable(GL10.GL_CULL_FACE);
		// which is the front? the one which is drawn counter clockwise
		// gl.glFrontFace(GL10.GL_CW);
		// which one should NOT be drawn
		// gl.glCullFace(GL10.GL_BACK);

		// gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		int[] textureNames = new int[1];
		// generate texture names:
		gl.glGenTextures(1, textureNames, 0);
		textureName = textureNames[0];
		
//		mTriangle = new Triangle(gl, mContext);

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
		setupDraw2D(gl);
		square = new float[] { -100f * _aspectRatio, -100.0f, -1f, 100f * _aspectRatio, -100.0f, -1f, -100f * _aspectRatio, 100.0f, -1f, 100f * _aspectRatio, 100.0f, -1f };

		squareBuffer = makeFloatBuffer(square);
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
	 * Make a direct NIO FloatBuffer from an array of floats
	 * 
	 * @param arr
	 *            The array
	 * @return The newly created FloatBuffer
	 */
	protected static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	/**
	 * Setup OpenGL to draw in 2D.
	 */
	private void setupDraw2D(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(-100.0f * _aspectRatio, 100.0f * _aspectRatio, -100.0f, 100.0f, 1.0f, -1.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	private int textureName;
	private float[] square;
	float[] textureCoords = new float[] {
			// Camera preview
			0.0f, 0.625f, 0.9375f, 0.625f, 0.0f, 0.0f, 0.9375f, 0.0f };
	private FloatBuffer textureBuffer;
	private FloatBuffer squareBuffer;

	private boolean isTextureInitialized = false;

	private Buffer frameData;;

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

//		Game.debug("Framerate", Float.toString(Game.fps()));
		text.setText(Game.getDebugMessage());

		// load new preview frame as a texture, if needed
		// if (frameEnqueued) {
		// frameLock.lock();
		if (isTextureInitialized) {
			setupDraw2D(gl);
			gl.glDisable(GL10.GL_DEPTH_TEST);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			// gl.glDisable(GL10.GL_LIGHTING);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName);
			// initializeTexture(gl);
			// } else {
			// // just update the image
			// // can we just update a portion(non power of two)?...seems to
			// // work
			gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, 0, 0, previewFrameWidth, previewFrameHeight, GL10.GL_RGB, GL10.GL_UNSIGNED_BYTE, frameData);
			// }
			// frameLock.unlock();
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			// frameEnqueued = false;
			// }

			gl.glColor4f(1, 1, 1, 1f);
			// draw camera preview frame:
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, squareBuffer);

			// draw camera square
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}

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
		// mSquare.draw(gl);

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

	public void setBackground(ByteBuffer frameBuffer) {
		frameData = frameBuffer;
		// byte[] frame;
		// frame = new byte[textureSize * textureSize * 3];
		// gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGB, textureSize,
		// textureSize, 0, GL10.GL_RGB, GL10.GL_UNSIGNED_BYTE,
		// ByteBuffer.wrap(frame));
		isTextureInitialized = true;
	}

	public void setPreviewFrameSize(int textureSize, int realWidth, int realHeight) {
		// test if it is a power of two number
		if (!GenericFunctions.isPowerOfTwo(textureSize))
			return;
		this.textureSize = textureSize;
		this.previewFrameHeight = realHeight;
		this.previewFrameWidth = realWidth;
		// calculate texture coords
		this.textureCoords = new float[] {
				// Camera preview
				0.0f, ((float) realHeight) / textureSize, ((float) realWidth) / textureSize, ((float) realHeight) / textureSize, 0.0f, 0.0f, ((float) realWidth) / textureSize,
				0.0f };
		textureBuffer = makeFloatBuffer(textureCoords);
	}
}
