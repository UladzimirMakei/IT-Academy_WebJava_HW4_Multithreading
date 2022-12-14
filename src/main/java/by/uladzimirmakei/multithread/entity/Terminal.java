package by.uladzimirmakei.multithread.entity;

import by.uladzimirmakei.multithread.util.TerminalIdGenerator;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

@Getter
public class Terminal {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int STATION_UNLOAD_SPEED = 100;
    private static final int EMPTY_VAN_LOAD = 0;

    private final long terminalId;

    public Terminal() {
        this.terminalId = TerminalIdGenerator.getId();
    }

    public void unload(Van van) {
        LOGGER.info("{} van is unloading on terminal {}." +
                        " Van load is {} kg",
                van.getVanId(), terminalId, van.getVanLoadKg());
        try {
            int unloadTime = calculateUnloadTime(van);
            for (int i = 1; i <= unloadTime; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(van.getVanId()
                        + " van unloads for " + i);
            }
        } catch (InterruptedException e) {
            LOGGER.warn("TerminalBlock caught exception {}",
                    e.getMessage());
            Thread.currentThread().interrupt();
        }
        van.setVanLoadKg(EMPTY_VAN_LOAD);
    }

    private int calculateUnloadTime(Van van) {
        return (van.getVanLoadKg() / STATION_UNLOAD_SPEED);
    }
}
