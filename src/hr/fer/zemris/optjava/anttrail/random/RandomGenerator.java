package hr.fer.zemris.optjava.anttrail.random;

import java.util.Random;

public class RandomGenerator {

    private static Random random = new Random();

    public static int nextInt(int bound) {
        if (bound == 0) {
            return 0;
        }
        return random.nextInt(bound);
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

}
