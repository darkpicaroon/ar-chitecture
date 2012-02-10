package com.test.meshcolortexture;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawingImages implements ApplicationListener {

	private SpriteBatch batch;
	private Texture texture;
	private TextureRegion region;
	private Sprite sprite;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		 batch = new SpriteBatch();
		 texture = new Texture(Gdx.files.internal("data/82.jpg"));
		 region = new TextureRegion(texture, 0,0, 64, 64);
		 sprite = new Sprite(texture, 0, 0, 256, 256);
		 sprite.setPosition(10, 10);
		 sprite.setColor(0, 0, 1, 1);
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
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        batch.setColor(1, 0, 0, 1);
        batch.draw(texture, 10, 10);
        batch.setColor(0, 1, 0, 1);
        batch.draw(texture, 10, 10);
        sprite.draw(batch);
        batch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
