package by.uladzimirmakei.multithread.entity;

import by.uladzimirmakei.multithread.exception.VanMultiThreadException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UnloadStation {

    private static final int STATION_UNLOAD_SPEED = 100;
    private static Logger logger = LogManager.getLogger();
    private AtomicInteger unloadCounter;

    public UnloadStation() {
        unloadCounter = new AtomicInteger(0);
    }

    public void enterUnloadStation(Van van) throws VanMultiThreadException {
        int unloadSpeed = calculateUnloadTime(van);
        logger.log(Level.DEBUG, "{} van is unloading. Speed is {}",
                Thread.currentThread().getName(), unloadSpeed);
        try {
            for (int i = 1; i <= unloadSpeed; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()
                        + " sleeps for " + i);
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Unload station caught exception {}",
                    e.getMessage());
            throw new VanMultiThreadException("Thread in unload station");
        }
        van.setVanLoad(VanLoadType.EMPTY);
        logger.log(Level.DEBUG, "{}  van after unloading is {}. Currently unloading {}",
                Thread.currentThread().getName(), van.getVanLoad(), unloadCounter.decrementAndGet());
    }

    private int calculateUnloadTime(Van van) {
        return (van.getVanLoad().getGoodsAmount() / STATION_UNLOAD_SPEED);
    }

    public int getUnloadingCounter() {
        return (unloadCounter.get() >= 3) ? unloadCounter.get() : unloadCounter.getAndIncrement();
    }
}
