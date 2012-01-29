package tutorial.vortex.primitives;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tutorial.vortex.helper.Mesh;
import tutorial.vortex.helper.MeshLoader;
import tutorial.vortex.helper.Texture;
import tutorial.vortex.helper.Texture.TextureFilter;
import tutorial.vortex.helper.Texture.TextureWrap;

public class Synagoge {
	// new object variables we need
	// a raw buffer to hold indices
	private ShortBuffer _indexBuffer;

	// a raw buffer to hold the vertices
	private FloatBuffer _vertexBuffer;

	private short[] _indicesArray = { 0, 1, 2 };
	private int _nrOfVertices = 3;

	Mesh mesh;

	private StillModel temp;

	// code snipped
	public Synagoge(GL10 gl, Context ctx) {
		loadMesh(gl, ctx);
//		Bitmap bitmap;
//		try {
//			bitmap = BitmapFactory.decodeStream(ctx.getAssets().open("ship.png"));
//			shipTexture = new Texture(gl, bitmap, TextureFilter.MipMap, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
//			bitmap.recycle();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	void loadMesh(GL10 gl, Context ctx) {
		try {
//			temp = new ObjLoader().loadObj(Gdx.files.internal("modell_teil.obj"));
			
//			mesh = temp.subMeshes[0].mesh;
			mesh = MeshLoader.loadObj(gl, ctx.getAssets().open("modell_klein.obj"));
		} catch (IOException e) {
//			 TODO Auto-generated catch block
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
//		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING); 
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 0);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		
//		shipTexture.bind();
		gl.glPushMatrix();
		temp.render();
//		getMesh().render(Mesh.PrimitiveType.Triangles);
		gl.glPopMatrix();
		
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHT0);
		gl.glDisable(GL10.GL_LIGHTING);
//		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
}
