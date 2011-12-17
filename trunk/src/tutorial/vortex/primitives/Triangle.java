package tutorial.vortex.primitives;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tutorial.vortex.helper.Mesh;
import tutorial.vortex.helper.MeshLoader;
import tutorial.vortex.helper.Texture;
import tutorial.vortex.helper.Texture.TextureFilter;
import tutorial.vortex.helper.Texture.TextureWrap;

public class Triangle {
	// new object variables we need
	// a raw buffer to hold indices
	private ShortBuffer _indexBuffer;

	// a raw buffer to hold the vertices
	private FloatBuffer _vertexBuffer;

	private short[] _indicesArray = { 0, 1, 2 };
	private int _nrOfVertices = 3;

	Mesh mesh;

	// code snipped
	public Triangle(GL10 gl, Context ctx) {
		loadMesh(gl, ctx);
		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeStream(ctx.getAssets().open("ship.png"));

			shipTexture = new Texture(gl, bitmap, TextureFilter.MipMap, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
			bitmap.recycle();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// float[] coords = { -0.5f, -0.5f, 0.5f, // 0
		// 0.5f, -0.5f, 0.5f, // 1
		// 0f, -0.5f, -0.5f, // 2
		// 0f, 0.5f, 0f, // 3
		// };
		// _nrOfVertices = coords.length;
		//
		// float[] colors = { 1f, 0f, 0f, 1f, // point 0 red
		// 0f, 1f, 0f, 1f, // point 1 green
		// 0f, 0f, 1f, 1f, // point 2 blue
		// 1f, 1f, 1f, 1f, // point 3 white
		// };
		//
		// short[] indices = new short[] { 0, 1, 3, // rwg
		// 0, 2, 1, // rbg
		// 0, 3, 2, // rbw
		// 1, 2, 3, // bwg
		// };
		//
		// // float has 4 bytes, coordinate * 4 bytes
		// ByteBuffer vbb = ByteBuffer.allocateDirect(coords.length * 4);
		// vbb.order(ByteOrder.nativeOrder());
		// _vertexBuffer = vbb.asFloatBuffer();
		//
		// // short has 2 bytes, indices * 2 bytes
		// ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		// ibb.order(ByteOrder.nativeOrder());
		// _indexBuffer = ibb.asShortBuffer();
		//
		// // float has 4 bytes, colors (RGBA) * 4 bytes
		// ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		// cbb.order(ByteOrder.nativeOrder());
		// _colorBuffer = cbb.asFloatBuffer();
		//
		// _vertexBuffer.put(coords);
		// _indexBuffer.put(indices);
		// _colorBuffer.put(colors);
		//
		// _vertexBuffer.position(0);
		// _indexBuffer.position(0);
		// _colorBuffer.position(0);
	}

	void loadMesh(GL10 gl, Context ctx) {
		try {
			mesh = MeshLoader.loadObj(gl, ctx.getAssets().open("ship.obj"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Mesh getMesh() {
		return mesh;
	}

	Texture shipTexture;
	private FloatBuffer _colorBuffer;
	float[] direction = { 1, 0.5f, 0, 0 };

	public void draw(GL10 gl) {
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 0);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		shipTexture.bind();
		// gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// gl.glColor4f(0.2f, 0.2f, 1, 0.7f);
		gl.glPushMatrix();
		getMesh().render(Mesh.PrimitiveType.Triangles);
		gl.glPopMatrix();
		// gl.glColor4f(1, 1, 1, 1);
		// gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHT0);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
}
