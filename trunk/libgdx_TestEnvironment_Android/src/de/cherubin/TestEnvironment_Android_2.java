package de.cherubin;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.test.meshcolortexture.TestEnvironment;
import com.test.meshcolortexture.TestEnvironmentRenderer;

import de.cherubin.extern.MyAndroidApplication;

import android.os.Bundle;

public class TestEnvironment_Android_2 extends MyAndroidApplication {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useCompass = true;
		config.useWakelock = false;
		// _app=new TestEnvironment();
		initialize(new TestEnvironmentRenderer(), config);
//		initialize(new TestEnvironment(), config);
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
	}

}
