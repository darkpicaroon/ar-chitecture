package de.cherubin.helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;

public class Pyramid {
	public float color = Color.toFloatBits(0, 100, 100, 100);
	public float topColor = Color.toFloatBits(200, 20, 0, 100);
	private Mesh faces;
	private float edge = 0.3f;

	public void create() {
		if (faces == null) {
			createCube();
		}
	}

	public void updateGrid() {
		createCube();
	}

	private void createCube() {

		faces = new Mesh(true, 18, 18, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

		 short[] f=new short[18];
		 for (int i=0;i<18;i++)f[i]=(short) i;
		faces.setIndices(f);

		faces.setVertices(new float[] {
				
				edge, 0, 0, color,
				-edge, 0, 0, color,
				0, edge, 0, color,
				
				edge, 0, 0, color,
				-edge, 0, 0, color,
				0, -edge, 0, color,
				
				0, 0, 2*edge, topColor ,
				edge, 0, 0, color,
				0, edge, 0, color,
				
				0, 0, 2*edge, topColor ,
				-edge, 0, 0, color,
				0, -edge, 0, color,
				
				0, 0, 2*edge, topColor ,
				0, -edge, 0, color,
				edge,0 , 0, color,
				
				0, 0, 2*edge, topColor ,
				0, edge, 0, color,
				-edge, 0, 0, color,
				});
	}

	public void render(ImmediateModeRenderer renderer) {
		faces.render(GL11.GL_TRIANGLES, 0, 18);
	}
}
