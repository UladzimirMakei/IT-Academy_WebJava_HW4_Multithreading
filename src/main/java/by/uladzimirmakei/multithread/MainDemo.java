package by.uladzimirmakei.multithread;

import by.uladzimirmakei.multithread.entity.Van;
import by.uladzimirmakei.multithread.entity.VanLoadType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainDemo {
    private MainDemo() {
    }

    public static void main(String[] args) {

        System.out.println("Available number of cores: "
                + Runtime.getRuntime().availableProcessors());
        Van vanOne = new Van(VanLoadType.FULL);
        Van vanTwo = new Van(VanLoadType.HALF);
        Van vanThree = new Van(VanLoadType.HALF);
        Van vanFour = new Van(VanLoadType.LITTLE);
        Van vanFive = new Van(VanLoadType.FULL);

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
