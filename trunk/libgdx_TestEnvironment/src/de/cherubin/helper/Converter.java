package de.cherubin.helper;

public class Converter {
	/**
	 * converts the given degrees to radians
	 * param degree given degrees
	 * return the radians
	 */
	public static float degreeToRadian(float degree) {
		return (float) ((degree / 180.0f) * Math.PI);
	}
	/** converts the given radian to degrees
	 * param radian the given radian
	 * return the degrees
	 */
	public static float radianToDegree(float radian) {
		return (float) ((radian / Math.PI) * 180.0f);
	}
}
