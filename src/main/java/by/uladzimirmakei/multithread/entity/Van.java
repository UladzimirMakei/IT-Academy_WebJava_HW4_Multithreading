package by.uladzimirmakei.multithread.entity;

import by.uladzimirmakei.multithread.exception.VanMultiThreadException;
import by.uladzimirmakei.multithread.repository.LogisticBaseRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class Van implements Callable<Void> {
    private static Logger logger = LogManager.getLogger();
    private VanLoadType vanLoad;

    @Override
    public Void call() {
        logger.log(Level.DEBUG, "{} New van thread is created. Current load {}",
                Thread.currentThread().getName(), this.getVanLoad());

        LogisticBaseRepository baseRepository = LogisticBaseRepository
                .getInstance();
        try {
            baseRepository.enterQueue(this);
        } catch (VanMultiThreadException e) {
            logger.log(Level.ERROR, "Van exception was caught while unloading {}",
                    e.getMessage());
        }

        logger.log(Level.DEBUG, "{}  van after unloading is {}"
                , Thread.currentThread().getName(), this.getVanLoad());
        return null;
    }
}
