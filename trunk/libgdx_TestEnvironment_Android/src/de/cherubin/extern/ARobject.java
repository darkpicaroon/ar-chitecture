package de.cherubin.extern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class ARobject {
	private StillModel model;
	private PhysicalLocation location;
	private float[] translateOffset=new float[]{0,0,0};
	
	public ARobject(StillModel model) {
		this.model = model;
	}

	public void setTranslateOffset(float x,float y,float z){
		translateOffset[0]=x;
		translateOffset[1]=y;
		translateOffset[2]=z;
	}
	
	public void render(){
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glTranslatef(20 + translateOffset[0], translateOffset[1], translateOffset[2]);
		// Gdx.gl10.glRotatef(turnEast + rotationAni, 0.f, 1.f, 0.f);
		Gdx.gl10.glRotatef(-90, 0.f, 1.f, 0.f);
		Gdx.gl10.glRotatef(90, 1.f, 0.f, 0.f);
		// cube.render();
		model.render();
		Gdx.gl10.glPopMatrix();
	}

	public void setLocation(PhysicalLocation location) {
		this.location = location;
	}

	public PhysicalLocation getLocation() {
		return location;
	}
}
