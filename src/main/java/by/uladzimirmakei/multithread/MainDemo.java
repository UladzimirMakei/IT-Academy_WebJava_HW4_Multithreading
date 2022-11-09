package by.uladzimirmakei.multithread;

import by.uladzimirmakei.multithread.entity.Van;
import by.uladzimirmakei.multithread.entity.VanLoadType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainDemo {

    private MainDemo() {
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Available number of cores: "
                + Runtime.getRuntime().availableProcessors());
        Callable<Void> vanOne = new Van(VanLoadType.FULL);
        Callable<Void> vanTwo = new Van(VanLoadType.HALF);
        Callable<Void> vanThree = new Van(VanLoadType.HALF);
        Callable<Void> vanFour = new Van(VanLoadType.LITTLE);
        Callable<Void> vanFive = new Van(VanLoadType.FULL);

        ExecutorService service = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());

        ArrayList<Callable<Void>> todo = new ArrayList<>(5);

        todo.add(vanOne);
        todo.add(vanTwo);
        todo.add(vanThree);
        todo.add(vanFour);
        todo.add(vanFive);

        List<Future<Void>> futures = service.invokeAll(todo);
        service.shutdown();
    }
}
