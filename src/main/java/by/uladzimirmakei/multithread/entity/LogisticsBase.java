package by.uladzimirmakei.multithread.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticsBase {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int TERMINAL_NUMBER = 2;

    private static LogisticsBase baseRepository;
    private final Lock getReleaseLock = new ReentrantLock();

    private Deque<Condition> waitingQueue;
    private Deque<Terminal> availableTerminalDeque;

    private LogisticsBase() {
        waitingQueue = new ArrayDeque<>();
        availableTerminalDeque = new ArrayDeque<>();
        for (int i = 0; i < TERMINAL_NUMBER; i++) {
            Terminal terminal = new Terminal();
            availableTerminalDeque.add(terminal);
        }
    }

    public static synchronized LogisticsBase getInstance() {
        if (baseRepository == null) {
            baseRepository = new LogisticsBase();
        }
        return baseRepository;
    }

    public Terminal getTerminal(boolean isPerishable) {
        try {
            getReleaseLock.lock();
            try {
                while (availableTerminalDeque.isEmpty()) {
                    Condition condition = getReleaseLock.newCondition();
                    if (isPerishable) {
                        waitingQueue.addFirst(condition);
                    } else {
                        waitingQueue.addLast(condition);
                    }
                    condition.await();
                }
            } catch (InterruptedException e) {
                LOGGER.warn("Logistics base caught exception {}",
                        e.getMessage());
                Thread.currentThread().interrupt();
            }
            return availableTerminalDeque.removeFirst();
        } finally {
            getReleaseLock.unlock();
        }
    }

    public void releaseTerminal(Terminal terminal) {
        try {
            getReleaseLock.lock();
            availableTerminalDeque.push(terminal);
            Condition condition = waitingQueue.pollFirst();
            if (condition != null) {
                condition.signal();
            }
        } finally {
            getReleaseLock.unlock();
        }
    }
}
