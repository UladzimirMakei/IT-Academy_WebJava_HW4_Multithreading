package by.uladzimirmakei.multithread.action.impl;

import by.uladzimirmakei.multithread.entity.Van;
import by.uladzimirmakei.multithread.entity.VanLoadType;
import by.uladzimirmakei.multithread.action.UnloadVanAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class UnloadVanActionImpl implements UnloadVanAction<Van> {

    private static final int STATION_UNLOAD_SPEED = 100;
    private static Logger logger = LogManager.getLogger();

    @Override
    public void unload(Van van) throws InterruptedException {
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
            logger.log(Level.ERROR, "Base unload manager caught exception {}",
                    e.getMessage());
            throw new InterruptedException();
        }
        van.setVanLoad(VanLoadType.EMPTY);
    }

    private int calculateUnloadTime(Van van) {
        return (van.getVanLoad().getGoodsAmount() / STATION_UNLOAD_SPEED);
    }
}
