package by.uladzimirmakei.multithread.service.impl;

import by.uladzimirmakei.multithread.entity.Van;
import by.uladzimirmakei.multithread.entity.VanLoadType;
import by.uladzimirmakei.multithread.service.BaseUnloadManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

import static by.uladzimirmakei.multithread.entity.LogisticBaseRepository.STATION_UNLOAD_SPEED;

public class BaseUnloadManagerImpl implements BaseUnloadManager<Van> {
    private static Logger logger = LogManager.getLogger();

    @Override
    public synchronized void unload(Van van) throws InterruptedException {
        System.out.println("Unloading new van"
                + Thread.currentThread().getName());
        int unloadSpeed = calculateUnloadTime(van, STATION_UNLOAD_SPEED);
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

    private int calculateUnloadTime(Van van, int unloadSpeed) {
        return (van.getVanLoad().getGoodsAmount() / unloadSpeed);
    }
}
