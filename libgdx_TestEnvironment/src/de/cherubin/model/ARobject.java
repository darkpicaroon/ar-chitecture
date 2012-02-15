package de.cherubin.model;

import java.util.Timer;
import java.util.TimerTask;

import android.location.Location;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Vector3;

public class ARobject {
	private static final long delay = 0;
	private static final long interval = 1000;
	private StillModel model;
	private PhysicalLocation physicalLocation;
	private float[] translateOffset = new float[] { 0, 0, 0 };
	protected double distance = 0.0;
	private float[] dist = new float[1];
	private Vector3 locationVector = new Vector3(0, 0, 0);
	private Timer timer;

	public ARobject(StillModel model) {
		this.model = model;
		setTimer();
	}

	public void translateOffset(float x, float y, float z) {
		translateOffset[0] = x;
		translateOffset[1] = y;
		translateOffset[2] = z;
	}

	private double getLatitude() {
		return physicalLocation.getLatitude();
	}

	private double getLongitude() {
		return physicalLocation.getLongitude();
	}

	private double getDistance() {
		return distance;
	}

	private void updateDistance(Location location) {
		if (location == null)
			throw new NullPointerException();

		Location.distanceBetween(getLatitude(), getLongitude(), location.getLatitude(), location.getLongitude(), dist);
		distance = dist[0];
	}

	private void updateDistance(PhysicalLocation location) {
		if (location == null)
			throw new NullPointerException();

		Location.distanceBetween(getLatitude(), getLongitude(), location.getLatitude(), location.getLongitude(), dist);
		distance = dist[0];
	}

	/**
	 * Calculate the relative position of this Marker from the given Location.
	 * 
	 * @param location
	 *            Location to use in the relative position.
	 * @throws NullPointerException
	 *             if Location is NULL.
	 */
	public void calcRelativePosition(Location location) {
		if (location == null)
			throw new NullPointerException();

		updateDistance(location);
		// An elevation of 0.0 probably means that the elevation of the
		// POI is not known and should be set to the users GPS height
		if (physicalLocation.getAltitude() == 0.0)
			physicalLocation.setAltitude(location.getAltitude());

		// compute the relative position vector from user position to POI
		// location
		PhysicalLocation.convLocationToMixVector(location, physicalLocation, locationVector);
	}

	private void calcRelativePosition(PhysicalLocation userGPS) {
		if (userGPS == null)
			throw new NullPointerException();

		updateDistance(userGPS);
		// An elevation of 0.0 probably means that the elevation of the
		// POI is not known and should be set to the users GPS height
		if (physicalLocation.getAltitude() == 0.0)
			physicalLocation.setAltitude(userGPS.getAltitude());

		// compute the relative position vector from user position to POI
		// location
		PhysicalLocation.convLocationToMixVector(userGPS, physicalLocation, locationVector);
	}

	public void render() {
		Gdx.gl10.glPushMatrix();
		// Gdx.gl10.glTranslatef(20 + translateOffset[0], translateOffset[1],
		// translateOffset[2]);
		Gdx.gl10.glTranslatef(getLocationVector().x, getLocationVector().y, getLocationVector().z);
		// Gdx.gl10.glRotatef(turnEast + rotationAni, 0.f, 1.f, 0.f);
		Gdx.gl10.glRotatef(-90, 0.f, 1.f, 0.f);
		Gdx.gl10.glRotatef(90, 1.f, 0.f, 0.f);

		// cube.render();
		model.render();
		Gdx.gl10.glPopMatrix();
	}

	private Vector3 getLocationVector() {
		return locationVector;
	}

	public void setLocation(PhysicalLocation location) {
		this.physicalLocation = location;
	}

	public PhysicalLocation getLocation() {
		return physicalLocation;
	}

	public void setTimer() {
		timer = new Timer();
		timer.schedule(new RemindTask(), delay, // initial delay
				interval); // subsequent rate
	}

	class RemindTask extends TimerTask {
		public void run() {
			setLocation(GPSdata.next());
			calcRelativePosition(GPSdata.userGPS);
			// Log.d("ARobject", getLocationVector().toString());
		}

	}
}
