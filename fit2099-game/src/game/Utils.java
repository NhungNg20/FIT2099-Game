package game;

import java.util.Random;

/**
 * This class contains utility functions for the game package.
 */
public final class Utils {
	// Same generator used by entire application.
	// Avoids different instance being created with same seed. [Also allows for deterministic game ]
	private static final Random randObj = new Random();

	/**
	 * Randomly generates an integer between 0 (inclusive) and max (exclusive).
	 * @param max The upper bound for the integer result.
	 * @return A random integer between 0 (inclusive) and max (exclusive).
	 */
	public static int randInt(int max) {
		return randObj.nextInt(max);
	}

	/**
	 * Calculates a boolean outcome with the given probability.
	 * @throws IllegalArgumentException if probability not within 0.0 and 1.0 inclusive.
	 * @param probability The probability of the outcome being true (between 0.0 and 1.0 inclusive).
	 * @return true iff a pseudo random float between 0 and 1 is less than the given probability.
	 */
	public static boolean trueWithProbability(float probability) {
		if (0 > probability || 1 < probability)
			throw new IllegalArgumentException("The probability must be between 0 and 1 inclusive.");
		return Utils.randObj.nextFloat() < probability;
	}
}
