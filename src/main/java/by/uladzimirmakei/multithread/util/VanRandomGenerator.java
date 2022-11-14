package by.uladzimirmakei.multithread.util;

import java.util.Random;

public final class VanRandomGenerator {
    private static final Random RANDOM = new Random();

    private VanRandomGenerator() {
    }

    public static int getRandomLoadKg() {
        return (RANDOM.nextInt(900) + 100);
    }

    public static boolean getRandomPerishable() {
        return RANDOM.nextBoolean();
    }
}
