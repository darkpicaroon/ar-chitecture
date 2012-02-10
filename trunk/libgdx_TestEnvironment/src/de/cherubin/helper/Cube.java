package de.cherubin.helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class Cube {
	public float color = Color.toFloatBits(205,205,0, 100);
	private Mesh[] faces;
	private float edge = 0.5f;

	public Cube() {
		create();
	}

	public void create() {
		if (faces == null) {
			createCube();
		}
	}

	public void updateGrid() {
		createCube();
	}

	private void createCube() {
		faces = new Mesh[6];

		for (int i = 0; i < 6; i++) {
			faces[i] = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,
					"a_color"));

			faces[i].setIndices(new short[] { 0, 1, 2, 3 });
		}

		faces[0].setVertices(new float[] { edge, edge, edge, color, -edge, edge, edge, color, edge, -edge, edge, color, -edge, -edge, edge,
				color });

		faces[1].setVertices(new float[] { edge, edge, -edge, color, -edge, edge, -edge, color, edge, -edge, -edge, color, -edge, -edge,
				-edge, color });

		faces[2].setVertices(new float[] { edge, edge, -edge, color, -edge, edge, -edge, color, edge, edge, edge, color, -edge, edge, edge,
				color });

		faces[3].setVertices(new float[] { edge, -edge, -edge, color, -edge, -edge, -edge, color, edge, -edge, edge, color, -edge, -edge,
				edge, color });

		faces[4].setVertices(new float[] { edge, edge, edge, color, edge, -edge, edge, color, edge, edge, -edge, color, edge, -edge, -edge,
				color });

		faces[5].setVertices(new float[] { -edge, edge, edge, color, -edge, -edge, edge, color, -edge, edge, -edge, color, -edge, -edge,
				-edge, color });
	}

	public void render() {
		for (Mesh face : faces) {
			face.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}
	}
}
