package de.cherubin.helper;

import com.badlogic.gdx.math.Vector3;

public class CircleAnimation {

	private float mRadius=0.0005f;
	private Vector3 mCenter;
	private float mAngle=0;
	private float tempAngle=0;

	public CircleAnimation(float radius, Vector3 center) {
		this.mRadius = radius;
		this.mCenter = center;
	}

	public void setRadius(float radius) {
		mRadius = radius;
	}

	public Vector3 move() {
		tempAngle+=mAngle;
		float x = (float) (mRadius * Math.cos(tempAngle));
		float y = (float) (mRadius * Math.sin(tempAngle));
		final float z = 0f;
		return new Vector3(mCenter.cpy().add(x, y, z));
	}

	public void setAngle(float angle) {
		this.mAngle = angle;
	}

	public float getAngle() {
		return mAngle;
	}
}
