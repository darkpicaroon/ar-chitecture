package de.cherubin.model;

import java.util.ArrayList;

public class GPSdata {

	public static PhysicalLocation userGPS;
	public static ArrayList<PhysicalLocation> gpsPoints;
	private static int index = 0;
	public static final double[] latlon = new double[] { 51.05223152, 13.74708295, 51.05201908, 13.74701589, 51.05194658, 13.74672353,
			51.05202245, 13.74625683, 51.05226187, 13.74611735, 51.05250804, 13.74630779, 51.0525856, 13.74672085, 51.05246757, 13.747091 };

	static {
		userGPS = new PhysicalLocation();
		userGPS.set(51.05222478, 13.74673426, 0);
		gpsPoints = new ArrayList<PhysicalLocation>();
		for (int i = 0; i < 8; i++) {
			PhysicalLocation point = new PhysicalLocation();
			point.set(latlon[2 * i], latlon[2 * i + 1], 0);
			gpsPoints.add(point);
		}
	}

	public static PhysicalLocation next() {
		index++;
		if (index >= gpsPoints.size())
			index = 0;
		return gpsPoints.get(index);
	}

	public static String debug() {
		return "lat"+gpsPoints.get(index).getLatitude()+"lon: "+gpsPoints.get(index).getLatitude();
	}
}
