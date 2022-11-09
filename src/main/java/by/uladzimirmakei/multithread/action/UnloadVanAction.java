package by.uladzimirmakei.multithread.action;

import by.uladzimirmakei.multithread.exception.VanMultiThreadException;

public interface UnloadVanAction<T> {
    void unload(T input) throws InterruptedException, VanMultiThreadException;

}
