package de.cherubin.extern;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.backends.android.AndroidGraphics;
import com.badlogic.gdx.backends.android.AndroidInput;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewCupcake;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public class MyAndroidApplication extends AndroidApplication {
	private ARchitectureCamLayer mPreview;


	@Override
	public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
		config.a = 8;
		config.b = 8;
		config.g = 8;
		config.r = 8;
		config.depth = 16;
		config.stencil = 0;
		graphics = new AndroidGraphics(this, config, config.resolutionStrategy == null ? new FillResolutionStrategy()
				: config.resolutionStrategy);
		input = new AndroidInput(this, graphics.getView(), config);
		audio = new AndroidAudio(this);
		files = new AndroidFiles(this.getAssets());
		this.listener = listener;
		this.handler = new Handler();

		Gdx.app = this;
		Gdx.input = this.getInput();
		Gdx.audio = this.getAudio();
		Gdx.files = this.getFiles();
		Gdx.graphics = this.getGraphics();

		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		} catch (Exception ex) {
			log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
		}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		((GLSurfaceViewCupcake) graphics.getView()).getHolder().setFormat(PixelFormat.TRANSLUCENT);
		// ((GLSurfaceViewCupcake) graphics.getView()).setEGLConfigChooser(8, 8,
		// 8, 8, 16, 0);
		setContentView(graphics.getView(), createLayoutParams());
		// _vortexView = new ARchitectureView(this);
		// AndroidGraphics a = ((AndroidGraphics) this.getGraphics());
		// GLSurfaceViewCupcake v =(GLSurfaceViewCupcake) a.getView();
		mPreview = new ARchitectureCamLayer(this);
		addContentView(mPreview, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		createWakeLock(config);
	
	}


}
