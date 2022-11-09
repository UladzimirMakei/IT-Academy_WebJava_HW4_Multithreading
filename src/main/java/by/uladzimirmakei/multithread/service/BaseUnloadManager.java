package by.uladzimirmakei.multithread.service;

public interface BaseUnloadManager<T> {
    void unload(T input) throws InterruptedException;

}
