package by.uladzimirmakei.multithread.util;

public class TerminalIdGenerator {
    private static int id;

    private TerminalIdGenerator() {
    }

    public static int getId() {
        return ++id;
    }
}
