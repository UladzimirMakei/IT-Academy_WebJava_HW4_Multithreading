package by.uladzimirmakei.multithread.entity;

import by.uladzimirmakei.multithread.entity.exception.VanMultiThreadException;
import by.uladzimirmakei.multithread.action.UnloadVanAction;
import by.uladzimirmakei.multithread.action.impl.UnloadVanActionImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UnloadStation {
    private static final int UNLOAD_HANGAR_NUMBER = 3;
    private static Logger logger = LogManager.getLogger();
    private List<Van> vanUnloadingList;

    public UnloadStation() {
        vanUnloadingList = new ArrayList<>();
    }

    public void enterUnloadStation(Van van) throws VanMultiThreadException {
        vanUnloadingList.add(van);
        logger.log(Level.DEBUG, "{} van entered unload station",
                Thread.currentThread().getName());

        UnloadVanAction<Van> unloadManager = new UnloadVanActionImpl();
        try {
            unloadManager.unload(van);
        } catch (InterruptedException e) {
            throw new VanMultiThreadException(
                    "Van thread exception in unload station");
        }

        vanUnloadingList.remove(van);
    }

    public synchronized boolean isStationAvailable() {
        return (vanUnloadingList.size() < UNLOAD_HANGAR_NUMBER);
    }
}
