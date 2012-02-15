package com.test.meshcolortexture;

import tutorial.vortex.filter.MatrixFilter;

import android.hardware.SensorManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.g3d.G3dLoader;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.ValueChangedListener;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import de.cherubin.helper.Cube;
import de.cherubin.helper.Light;
import de.cherubin.helper.RenderMonitor;
import de.cherubin.model.ARobject;
import de.cherubin.model.GPSdata;

public class TestEnvironmentRenderer implements ApplicationListener {

	private PerspectiveCamera camera;
	private BitmapFont font;
	private SpriteBatch batch;
	StillModel model;
	float a = 0.1f;
	private StillModel synagoge;
	private float rotationAni = 0;
	private float movementIncrement = 0;
	private Cube cube;
	private Light light;
	private Stage stage;
	private Skin skin;

	@Override
	public void create() {

		// GUI
		skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(stage);

		final Slider slider = new Slider(0, 50, 1, skin.getStyle(SliderStyle.class), "slider");
		slider.setValue(10);
		slider.setValueChangedListener(new ValueChangedListener() {
			public void changed(Slider slider, float value) {
				sliderValue = value;
				Gdx.app.log("UI", "slider: " + value);
			}
		});

		Window window = new Window("Entfernung zur Synagoge", skin.getStyle(WindowStyle.class), "window");
		window.x = window.y = 0;
		window.defaults().pad(5);
		window.width(Gdx.graphics.getWidth());
		window.add(slider).width(Gdx.graphics.getWidth() - 40).expandX();
		window.pack();
		stage.addActor(window);

		// HUD
		font = new BitmapFont();
		batch = new SpriteBatch();

		// myObjects
		synagoge = G3dLoader.loadStillModel(Gdx.files.internal("data/synagoge.g3d"));
		synagoge.setMaterial(new Material("default"));

		ar_synagoge = new ARobject(synagoge);

		light = new Light();
		cube = new Cube();
		
		// OpenGL
		Gdx.gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		Gdx.gl.glClearColor(0, 0, 0, 0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	private float[] rotM = new float[16];
	private float[] tempM = new float[16];
	private float[] tempM2 = new float[16];
	private float turnEast = 90;
	private float sliderValue = 0;
	private ARobject ar_synagoge;

	@Override
	public void render() {

		float deltaTime = Gdx.graphics.getDeltaTime();
		rotationAni += 20 * deltaTime;
		if (rotationAni > 360) {
			// movementIncrement = -movementIncrement;
			rotationAni = 0;
		}

		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		RenderMonitor.debug("Framerate", Float.toString(RenderMonitor.fps()));
		RenderMonitor.debug("cubeRotation", Float.toString(rotationAni));
		RenderMonitor.debug("rotM", rotM[0] + " " + rotM[1] + " " + rotM[2]);

		Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glDisable(GL10.GL_LIGHTING);
		// Gdx.gl.glDisable(GL10.GL_CULL_FACE);

		batch.begin();
		font.drawMultiLine(batch, RenderMonitor.getDebugMessage(), 20, Gdx.graphics.getHeight() - 10);
		batch.end();
		//
		// stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		// stage.draw();

		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL10.GL_LIGHTING);
		// Gdx.gl.glEnable(GL10.GL_CULL_FACE);

		camera.update();
		camera.apply(Gdx.gl11);

		if (Gdx.input != null) {
			Gdx.input.getRotationMatrix(rotM);
			SensorManager.remapCoordinateSystem(rotM, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, tempM);
			MatrixFilter.LOWPASS.filter(tempM, tempM2);
		}
		Gdx.gl10.glMultMatrixf(tempM2, 0);

		light.render(Gdx.gl10, deltaTime, cube);

		ar_synagoge.translateOffset(sliderValue, 0, -2);
		ar_synagoge.render();

		// Gdx.app.log("Renderer", RenderMonitor.getDebugMessage());
		Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glDisable(GL10.GL_LIGHTING);
		// Gdx.gl.glDisable(GL10.GL_CULL_FACE);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL10.GL_LIGHTING);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		float aspectRatio = (float) width / (float) height;
		camera = new PerspectiveCamera(67, 1f * aspectRatio, 1f);
		camera.translate(0f, 0f, 0f);
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
		return builder.toString();
	}

	private float[] R = new float[16];

	public void updateR(float[] temp2) {
		R = temp2;
	}

}
