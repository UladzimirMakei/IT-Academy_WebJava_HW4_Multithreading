package by.uladzimirmakei.multithread;

import by.uladzimirmakei.multithread.creator.VanListCreator;
import by.uladzimirmakei.multithread.entity.Van;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainDemo {

    private MainDemo() {
    }

    public static void main(String[] args) {
        VanListCreator creator = new VanListCreator(5);
        List<Van> vanList = creator.createVanList();

        ExecutorService service = Executors.newFixedThreadPool(vanList.size());
        vanList.forEach(service::execute);
        service.shutdown();
    }
}
