package com.test.meshcolortexture;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class ProjectionViewportCamera implements ApplicationListener {

	private Mesh squareMesh;
	private PerspectiveCamera camera;
	private Mesh nearSquare;
	private int total= 0;
	private float movementIncrement= 0.0006f;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		if (squareMesh == null) {
			squareMesh = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

			squareMesh.setVertices(new float[] { 0, -0.5f, -4, Color.toFloatBits(128, 0, 0, 255), 1, -0.5f, -4, Color.toFloatBits(192, 0, 0, 255), 0, 0.5f, -4,
					Color.toFloatBits(192, 0, 0, 255), 1, 0.5f, -4, Color.toFloatBits(255, 0, 0, 255) });
			squareMesh.setIndices(new short[] { 0, 1, 2, 3 });
		}
		if (nearSquare == null) {
			nearSquare = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

			nearSquare.setVertices(new float[] { -1, -0.5f, -1.1f, Color.toFloatBits(0, 0, 128, 255), 0, -0.5f, -1.1f, Color.toFloatBits(0, 0, 192, 255), -1, 0.5f, -1.1f,
					Color.toFloatBits(0, 0, 192, 255), 0, 0.5f, -1.1f, Color.toFloatBits(0, 0, 255, 255) });
			nearSquare.setIndices(new short[] { 0, 1, 2, 3 });
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		total += 1;
		if (total > 500) {
			movementIncrement = -movementIncrement;
			total = -200;
		}

		camera.rotate(movementIncrement * 20, 0, 1, 0);
		camera.translate(movementIncrement, 0, movementIncrement);

		camera.update();
		camera.apply(Gdx.gl10);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		squareMesh.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
		nearSquare.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
