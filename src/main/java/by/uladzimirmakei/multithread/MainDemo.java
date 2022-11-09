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

        Callable<Integer> vanOne = new Van(VanLoadType.FULL);
        Callable<Integer> vanTwo = new Van(VanLoadType.HALF);
        Callable<Integer> vanThree = new Van(VanLoadType.HALF);
        Callable<Integer> vanFour = new Van(VanLoadType.LITTLE);
        Callable<Integer> vanFive = new Van(VanLoadType.FULL);

        ExecutorService service = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());

        ArrayList<Callable<Integer>> listVanThread = new ArrayList<>(5);

        listVanThread.add(vanOne);
        listVanThread.add(vanTwo);
        listVanThread.add(vanThree);
        listVanThread.add(vanFour);
        listVanThread.add(vanFive);

        List<Future<Integer>> futures = service.invokeAll(listVanThread);
        service.shutdown();
    }
}
