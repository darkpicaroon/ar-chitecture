package de.cherubin.helper;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.MathUtils;

public class Light {
	private float deltaPosition = 0;
	private float temp;

	public void render(GL10 gl, float deltaTime, Cube cube) {

//		temp += deltaTime;
//		deltaPosition = 10 * MathUtils.cos(temp);

//		Gdx.gl10.glPushMatrix();
//		Gdx.gl10.glTranslatef(0, 10, 30);
//		cube.render();
//		Gdx.gl10.glPopMatrix();

		// gl.glLoadIdentity();
		// Enable up to n=8 light sources: GL_LIGHTn
		gl.glEnable(GL10.GL_LIGHT0);
		// gl.glEnable(GL10.GL_LIGHT1);
		// gl.glEnable(GL10.GL_LIGHT2);
		// gl.glEnable(GL10.GL_LIGHT3);

		// light position
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[] { 0, 50, 50, 1 }, 0);

		// Light that has been reflected by other objects and hits the mesh in
		// small amounts
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, new float[] { 0.005f, 0.005f, 0.005f, 1f }, 0);

		// setting diffuse light color like a bulb or neon tube
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, new float[] { 0.9f, 0.9f, 0.7f, 1f }, 0);

		// setting specular light color like a halogen spot
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, new float[] { 0.9f, 0.9f, 0.7f, 1f }, 0);

		// directional or positional light
		// vector4f is x,y,z + w component
		// w=0 to create a directional light (x,y,z is the light direction) like
		// the sun
		// w=1 to create a positional light like a fireball.
		// FloatBuffer fb = BufferUtils.newFloatBuffer(8);
		// fb.put(new float[] { 1, 1, 1, 1 });
		// gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, fb);
	}
}
