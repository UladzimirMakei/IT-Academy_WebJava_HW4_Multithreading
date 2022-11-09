package by.uladzimirmakei.multithread.entity;

import by.uladzimirmakei.multithread.service.BaseUnloadManager;
import by.uladzimirmakei.multithread.service.impl.BaseUnloadManagerImpl;

import java.util.ArrayList;
import java.util.List;

public class LogisticBaseRepository {
    private static final int UNLOAD_STATION_AMOUNT = 3;
    private static LogisticBaseRepository baseRepository;
    private List<Van> vanQueueForUnload;
    private List<Van> unloadingList;

    private LogisticBaseRepository() {
        vanQueueForUnload = new ArrayList<>();
        unloadingList = new ArrayList<>();
    }

    public static synchronized LogisticBaseRepository getInstance() {
        if (baseRepository == null) {
            baseRepository = new LogisticBaseRepository();
        }
        return baseRepository;
    }

    public void enterQueue(Van newVan) {
        vanQueueForUnload.add(newVan);
        System.out.println("Van in queue to logistic base "
                + Thread.currentThread().getName());
        while (!isStationAvailable()) {
            try {
                newVan.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        enterUnloadStation(newVan);
        vanQueueForUnload.remove(newVan);
    }

    private void enterUnloadStation(Van newVan) {
        unloadingList.add(newVan);
        System.out.println("Van entered unload station "
                + Thread.currentThread().getName());
        BaseUnloadManager<Van> unloadManager = new BaseUnloadManagerImpl();
        try {
            unloadManager.unload(newVan);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        unloadingList.remove(newVan);
    }

    private boolean isStationAvailable() {
        return (unloadingList.size() < UNLOAD_STATION_AMOUNT);
    }
}
