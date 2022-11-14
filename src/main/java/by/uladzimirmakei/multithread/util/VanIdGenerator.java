package by.uladzimirmakei.multithread.util;

public final class VanIdGenerator {

    private static int id;

    private VanIdGenerator() {
    }

    public static int getId() {
        return ++id;
    }
}
