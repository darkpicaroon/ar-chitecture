package tutorial.vortex.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import tutorial.vortex.R;
import tutorial.vortex.filter.Util;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import android.widget.FrameLayout;

public class Background {
	private static final String TAG = "Background";

	private FloatBuffer vertexBuffer; // buffer holding the vertices
	int[] cameraTexture;
	byte[] glCameraFrame = new byte[256 * 256]; // size of a texture must be a
												// power of 2
	FloatBuffer texBuff;

	private float vertices[] = { -1.0f, -1.0f, 0.0f, // V1 - bottom left
			-1.0f, 1.0f, 0.0f, // V2 - top left
			1.0f, -1.0f, 0.0f, // V3 - bottom right
			1.0f, 1.0f, 0.0f // V4 - top right
	};
//	private float vertices[] = new float[] { -100f * RenderMonitor.aspectRatio, -100.0f, -1f, 100f * RenderMonitor.aspectRatio, -100.0f,
//			-1f, -100f * RenderMonitor.aspectRatio, 100.0f, -1f, 100f * RenderMonitor.aspectRatio, 100.0f, -1f };
	// private float camTexCoords[] = {
	// // Mapping coordinates for the vertices
	// 0.0f, 1.0f, // top left (V2)
	// 0.0f, 0.0f, // bottom left (V1)
	// 1.0f, 1.0f, // top right (V4)
	// 1.0f, 0.0f // bottom right (V3)
	// };
	private float camTexCoords[] = {
			// Camera preview
			0.0f, 0.625f, 0.9375f, 0.625f, 0.0f, 0.0f, 0.9375f, 0.0f };
	private FloatBuffer textureBuffer; // buffer holding the texture coordinates

	private int TexturID;

	public Background(GL10 gl) {
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());

		vertexBuffer = vertexByteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		// gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuff);
		// gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		bindCameraTexture(gl);
		// ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texture.length *
		// 4);
		// byteBuffer.order(ByteOrder.nativeOrder());
		// textureBuffer = byteBuffer.asFloatBuffer();
		// textureBuffer.put(camTexCoords);
		// textureBuffer.position(0);
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
			TexturID = cameraTexture[0];
			textureBuffer = Util.makeFloatBuffer(camTexCoords);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, TexturID);
			gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_LUMINANCE, 256, 256, 0, GL10.GL_LUMINANCE, GL10.GL_UNSIGNED_BYTE,
					ByteBuffer.wrap(glCameraFrame));
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		}
	}

	/** The texture pointer */
	private int[] textures = new int[1];

	/**
	 * The draw method for the square with the GL context
	 * 
	 * @param textures
	 */
	public void draw(GL10 gl) {
		// gl.glLoadIdentity();
		// gl.glRotatef(90.0f,0.0f,1.0f,0.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, TexturID);
		gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, 0, 0, 256, 256, 0, GL10.GL_UNSIGNED_BYTE, ByteBuffer.wrap(glCameraFrame));
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		// Draw the vertices as triangle strip
		// gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	public byte[] getFrameBuffer() {
		// Log.d(TAG, "getFrameBuffer");
		return glCameraFrame;
	}

	public void setFrameBuffer(byte[] frame) {
		// Log.d(TAG, "setFrameBuffer");
		glCameraFrame = frame;
	}
}