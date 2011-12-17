package tutorial.vortex.filter;

import android.util.Log;

public enum MatrixFilter {

	LOWPASS {
		@Override
		public float[] filter(float[] input, float[] output) {
			if (output == null)
				return input;
			for (int i = 0; i < input.length; i++) {
				output[i] = output[i] + ALPHA * (input[i] - output[i]);
			}
			return output;
		}
	},
	/**
	 * Works only with ONE!! 3x3 matrix!
	 * Used multiple times will overwrite buffer!
	 */
	MOVING_AVERAGE {
		private float[] x1 = new float[] { 0, 0, 0, 0 };
		private float[] x2 = new float[] { 0, 0, 0, 0 };
		private float[] x3 = new float[] { 0, 0, 0, 0 };

		private float[] y1 = new float[] { 0, 0, 0, 0 };
		private float[] y2 = new float[] { 0, 0, 0, 0 };
		private float[] y3 = new float[] { 0, 0, 0, 0 };

		private float[] z1 = new float[] { 0, 0, 0, 0 };
		private float[] z2 = new float[] { 0, 0, 0, 0 };
		private float[] z3 = new float[] { 0, 0, 0, 0 };

		private float[][] arrayContainer = new float[][] { x1, x2, x3, y1, y2, y3, z1, z2, z3 };

		@Override
		public float[] filter(float[] input, float[] output) {
			if (output == null)
				return input;
			for (int i = 0; i < input.length; i++) {
				float temp = average(update(arrayContainer[i], input[i]));
				Log.d("MOVING_AVERAGE",String.format("%f,%f,%f,%f", arrayContainer[i][0],arrayContainer[i][1],arrayContainer[i][2],arrayContainer[i][3]));
				output[i] = temp + ALPHA * (input[i] - temp);
			}
			return output;
		}

		private float[] update(float[] input, float newValue) {
			for (int i = 0; i < input.length - 1; i++)
				input[i] = input[i + 1];
			input[input.length - 1] = newValue;
			return input;
		}

		private float average(float[] input) {
			float sum = 0;
			for (int i = 0; i < input.length; i++)
				sum += input[i]; //
			return sum;
		}
	};

	public final float ALPHA = 0.2f;

	abstract public float[] filter(float[] input, float[] output);
}
