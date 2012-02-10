package de.semmel;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.test.meshcolortexture.TestEnvironment;
import com.test.meshcolortexture.TestEnvironmentRenderer;

public class TestEnvironment_Desktop {
	public static void main(String[] args) {
//		new LwjglApplication(new TestEnvironment(), "Game", 800, 600, false);
		new LwjglApplication(new TestEnvironmentRenderer(), "Game", 800, 600, false);
	}
}
