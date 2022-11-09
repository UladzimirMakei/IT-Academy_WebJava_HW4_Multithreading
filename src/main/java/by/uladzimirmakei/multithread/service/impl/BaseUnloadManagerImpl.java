package by.uladzimirmakei.multithread.service.impl;

import by.uladzimirmakei.multithread.entity.Van;
import by.uladzimirmakei.multithread.entity.VanLoadType;
import by.uladzimirmakei.multithread.service.BaseUnloadManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class BaseUnloadManagerImpl implements BaseUnloadManager<Van> {

    private static final int STATION_UNLOAD_SPEED = 100;
    private static Logger logger = LogManager.getLogger();

    @Override
    public synchronized void unload(Van van) throws InterruptedException {
        System.out.println("Unloading new van"
                + Thread.currentThread().getName());
        int unloadSpeed = calculateUnloadTime(van);
        System.out.println("current van speed is " + unloadSpeed);
        try {
            for (int i = 1; i <= unloadSpeed; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()
                        + " sleeps for " + i);
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Base unload manager caught exception {}",
                    e.getMessage());
            throw new InterruptedException();
        }
        van.setVanLoad(VanLoadType.EMPTY);
        notifyAll();
    }

    private int calculateUnloadTime(Van van) {
        return (van.getVanLoad().getGoodsAmount() / STATION_UNLOAD_SPEED);
    }
}
