package de.cherubin.helper;

public class LagrangeInterpolation {
	private double[] yi;
	private double[] xi;

	/**
	 * Doesn't work!!!!!!!!!!!!!!
	 * @param points
	 */
	public LagrangeInterpolation(double[] points) {
		yi = new double[points.length / 2];
		xi = new double[yi.length];
		for (int i = 0; i < points.length; i++) {
			xi[i] = points[i];
			yi[i] = points[i + 1];
		}
	}

	// Method to carry out the Aitken recursions.

	public double aitken(double x) {
		int n = xi.length - 1;
		double ft[] = (double[]) yi.clone();
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n - i; ++j) {
				ft[j] = (x - xi[j]) / (xi[i + j + 1] - xi[j]) * ft[j + 1] + (x - xi[i + j + 1]) / (xi[j] - xi[i + j + 1]) * ft[j];
			}
		}
		System.out.println("Interpolated value: " + ft[0]);
		return ft[0];
	}
}
