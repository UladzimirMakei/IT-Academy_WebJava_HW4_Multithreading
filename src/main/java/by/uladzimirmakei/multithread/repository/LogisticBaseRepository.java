package by.uladzimirmakei.multithread.repository;

import by.uladzimirmakei.multithread.entity.UnloadStation;
import by.uladzimirmakei.multithread.entity.Van;
import by.uladzimirmakei.multithread.exception.VanMultiThreadException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogisticBaseRepository {

    private static final int UNLOAD_HANGAR_NUMBER = 3;
    private static Logger logger = LogManager.getLogger();
    private static LogisticBaseRepository baseRepository;
    private UnloadStation unloadStation;

    private LogisticBaseRepository() {
        unloadStation = new UnloadStation();
    }

    public static synchronized LogisticBaseRepository getInstance() {
        if (baseRepository == null) {
            baseRepository = new LogisticBaseRepository();
        }
        return baseRepository;
    }

    public void enterQueue(Van van) throws VanMultiThreadException {
        logger.log(Level.DEBUG, "{} in queue to logistic base",
                Thread.currentThread().getName());
        synchronized (this) {
            while (!isStationAvailable()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR, "Exception in logistic queue {}",
                            e.getMessage());
                    Thread.currentThread().interrupt();
                    throw new VanMultiThreadException(
                            "Van thread is interrupted in logistic base queue");
                }
            }
        }
        try {
            unloadStation.enterUnloadStation(van);
        } catch (VanMultiThreadException e) {
            logger.log(Level.ERROR, "van exception while entering unload station {}",
                    e.getMessage());
        }
    }

    private boolean isStationAvailable() {
        return (unloadStation.getUnloadingCounter() < UNLOAD_HANGAR_NUMBER);
    }

    public synchronized void release() {
        this.notifyAll();
    }
}
