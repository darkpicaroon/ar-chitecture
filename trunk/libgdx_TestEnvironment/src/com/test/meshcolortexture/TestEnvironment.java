package com.test.meshcolortexture;

import android.hardware.SensorManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.g3d.G3dtLoader;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer10;
import com.badlogic.gdx.input.RemoteInput;
import com.badlogic.gdx.scenes.scene2d.ui.utils.AndroidClipboard;

import de.cherubin.helper.Cube;
import de.cherubin.helper.Grid3D;
import de.cherubin.helper.Pyramid;

public class TestEnvironment implements ApplicationListener {

	private PerspectiveCamera camera;
	private Grid3D grid;
	private BitmapFont font;
	private SpriteBatch batch;
	private ImmediateModeRenderer10 renderer;
	private Cube cube;

	private float z = 0;
	private float v;
	private float temp;
	private float pitch;
	private float roll;
	private float azimuth;
	private float oldPitch;
	private float newPitch;
	private float x = 0;
	private float y = 0;
	private float newRoll;
	private float oldRoll;
	private Pyramid pyramid;
	private float newAzi;
	private float oldAzi;
	private Mesh mesh;
	StillModel model;
	float a = 0.1f;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		// RemoteInput receiver = new RemoteInput(8190);
		// Gdx.input = receiver;
		// grid = new Grid3D();
		// grid.disableLayer(Grid3D.Z);
		// grid.disableLayer(Grid3D.Y);
		// grid.disableLayer(Grid3D.X);
		// grid.create();

		// cube = new Cube();
		// cube.create();
		//
		// pyramid = new Pyramid();
		// pyramid.create();
		//
		// input = Gdx.app.getInput();
		//
		font = new BitmapFont();
		batch = new SpriteBatch();
		// renderer = new ImmediateModeRenderer10();

		Gdx.gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		Gdx.gl.glClearColor(0, 0, 0, 0);
		// ObjLoader loader = new ObjLoader();
		// StillModel model =
		// loader.loadObj(Gdx.files.internal("data/modell_reduziert.obj"),
		// true);
		// G3dExporter.export(model, Gdx.files.absolute("data/reduziert.g3d"));
		// mesh =
		// ModelLoader.loadObj(Gdx.files.internal("data/mini_obj.obj").read());
		// com.badlogic.gdx.graphics.g3d.loaders.g3d.G3dLoader
		// model =
		// G3dtLoader.loadStillModel(Gdx.files.internal("data/test.g3dt"),
		// false);
		// model.setMaterial(new Material("default"));
		// model =
		// G3dLoader.loadStillModel(Gdx.files.internal("data/toyplane.g3d"));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	private int total = 0;
	private float movementIncrement = 0.06f;
	private float[] rotM = new float[16];
	private float[] tempM = new float[16];

	@Override
	public void render() {

		// camera.update();
		// camera.apply(Gdx.gl10);
		 Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		 batch.begin();
		 font.drawMultiLine(batch, getOrientationString(), 20,
		 Gdx.graphics.getHeight() - 10);
		 batch.end();
		// if (Gdx.input != null) {
		// // synchronized (this) {
		// Gdx.input.getRotationMatrix(rotM);
		// SensorManager.remapCoordinateSystem(rotM,
		// SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, tempM);
		// // }
		// }
		//
		// float deltaTime = Gdx.graphics.getDeltaTime();
		//
		// total += 1;
		// if (total > 500) {
		// movementIncrement = -movementIncrement;
		// total = -200;
		// }
		//
		// camera.rotate(movementIncrement * 20 * deltaTime, 0, 1, 0);
		// camera.translate(movementIncrement * deltaTime, movementIncrement *
		// deltaTime, movementIncrement * deltaTime);

		// camera.apply(Gdx.gl11);
		// Gdx.gl10.glMultMatrixf(tempM, 0);
		// grid.render(GL11.GL_LINES);
		// camera.update();

		//
		// if (input.isKeyPressed(Keys.UP) || input.isTouched()) {
		// resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// }
		//
		// float factor = 10f;
		// if (input.isKeyPressed(Keys.UP))
		// z += deltaTime * factor;
		// if (input.isKeyPressed(Keys.DOWN))
		// z -= deltaTime * factor;
		// if (input.isKeyPressed(Keys.LEFT))
		// x += deltaTime * factor;
		// if (input.isKeyPressed(Keys.RIGHT))
		// x -= deltaTime * factor;
		//
		// newPitch = a * (Gdx.input.getRoll() + 90) + (1 - a) * oldPitch;
		// oldPitch = newPitch;
		// newRoll = a * Gdx.input.getPitch() + (1 - a) * oldRoll;
		// oldRoll = newRoll;
		//
		// if ((oldAzi <= 0 && Gdx.input.getAzimuth() >= 0) || (oldAzi >= 0 &&
		// Gdx.input.getAzimuth() <= 0)) {
		// newAzi = Gdx.input.getAzimuth();
		// } else {
		// newAzi = a * Gdx.input.getAzimuth() + (1 - a) * oldAzi;
		// }
		// oldAzi = newAzi;
		//

		//
		// Gdx.gl11.glPushMatrix();
		//
		// Gdx.gl11.glRotatef(newPitch, 1, 0, 0);
		// // Gdx.gl11.glRotatef(newAzi, 0, 1, 0);
		// // Gdx.gl11.glRotatef(newRoll, 0, 0, 1);
		//
		// // renderer.begin(GL10.GL_LINES);
		// // renderer.vertex(0, 0, 0);
		// // renderer.vertex(0, 0, 1);
		// // renderer.end();
		//
		// // cube.render();
		// // pyramid.render(renderer);
		// // mesh.render(GL10.GL_TRIANGLES);

		// model.render();
		// Gdx.gl11.glPopMatrix();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		float aspectRatio = (float) width / (float) height;
		camera = new PerspectiveCamera(67, 1f * aspectRatio, 1f);
		camera.translate(0f, 2f, 0f);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	private String getOrientationString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\ndelta-Time: ");
		builder.append((float) Gdx.graphics.getDeltaTime());
		builder.append("\nazimuth: ");
		builder.append((int) Gdx.input.getAzimuth());
		builder.append("\npitch: ");
		builder.append((int) Gdx.input.getPitch());
		builder.append("\nroll: ");
		builder.append((int) Gdx.input.getRoll());
		builder.append("\nAccelerometer X: ");
		builder.append((int) Gdx.input.getAccelerometerX());
		builder.append("\nAccelerometer Y: ");
		builder.append((int) Gdx.input.getAccelerometerY());
		builder.append("\nAccelerometer Z: ");
		builder.append((int) Gdx.input.getAccelerometerZ());
		builder.append("\nfps: " + Gdx.graphics.getFramesPerSecond());
		builder.append("\nrotM:\n");
		builder.append(rotM[0] + " " + rotM[1] + " " + rotM[2]);
		return builder.toString();
	}

}
