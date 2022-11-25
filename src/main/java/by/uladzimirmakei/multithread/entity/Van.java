package by.uladzimirmakei.multithread.entity;

import by.uladzimirmakei.multithread.util.VanIdGenerator;
import by.uladzimirmakei.multithread.util.VanRandomGenerator;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
public class Van implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final long vanId;
    private int vanLoadKg;
    private boolean isPerishable;
    private State state;

    public Van() {
        this.vanId = VanIdGenerator.getId();
        this.vanLoadKg = VanRandomGenerator.getRandomLoadKg();
        this.isPerishable = VanRandomGenerator.getRandomPerishable();
        this.state = State.NEW;
    }

    private enum State {
        NEW, WAITING, UNLOADING, COMPLETED
    }

    @Override
    public void run() {
        LOGGER.info("{} van arrived at logistic base. "
                        + "Does it have perishable goods - {}",
                getVanId(), isPerishable);
        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        setState(State.WAITING);
        Terminal terminal = logisticsBase.getTerminal(isPerishable);
        setState(State.UNLOADING);
        terminal.unload(this);
        logisticsBase.releaseTerminal(terminal);
        LOGGER.info("{} van unloading is finished on terminal {}" +
                        ". Its load is {}",
                getVanId(), terminal.getTerminalId(), getVanLoadKg());
        setState(Van.State.COMPLETED);
    }
}
