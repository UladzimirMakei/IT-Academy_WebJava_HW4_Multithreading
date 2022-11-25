package by.uladzimirmakei.multithread.creator;

import by.uladzimirmakei.multithread.entity.Van;

import java.util.ArrayList;
import java.util.List;

public class VanListCreator {

    private int listSize;

    public VanListCreator(int listSize) {
        this.listSize = listSize;
    }

    public List<Van> createVanList() {
        List<Van> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            Van van = new Van();
            list.add(van);
        }
        return list;
    }
}
