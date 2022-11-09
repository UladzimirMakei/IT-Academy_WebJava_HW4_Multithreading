package by.uladzimirmakei.multithread.demo;

import by.uladzimirmakei.multithread.entity.Van;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static by.uladzimirmakei.multithread.entity.VanLoadType.*;

public class MainDemo {
    private MainDemo() {
    }

    public static void main(String[] args) {

        System.out.println("Available number of cores: "
                + Runtime.getRuntime().availableProcessors());
        Van vanOne = new Van(FULL);
        Van vanTwo = new Van(HALF);
        Van vanThree = new Van(HALF);
        Van vanFour = new Van(LITTLE);
        Van vanFive = new Van(FULL);

        ExecutorService service = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());

        service.execute(vanOne);
        service.execute(vanTwo);
        service.execute(vanThree);
        service.execute(vanFour);
        service.execute(vanFive);
        service.shutdown();
    }
}