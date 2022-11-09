package by.uladzimirmakei.multithread.entity;

import java.util.Objects;

public class Van implements Runnable {
    private VanLoadType vanLoad;

    public Van(VanLoadType vanLoad) {
        this.vanLoad = vanLoad;
    }

    public VanLoadType getVanLoad() {
        return vanLoad;
    }

    public void setVanLoad(VanLoadType vanLoad) {
        this.vanLoad = vanLoad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Van van = (Van) o;
        return vanLoad == van.vanLoad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vanLoad);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Van{");
        sb.append("loadType=").append(vanLoad);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void run() {
        System.out.println("New van thread is created "
                + Thread.currentThread().getName());
        LogisticBaseRepository baseRepository = LogisticBaseRepository
                .getInstance();
        baseRepository.enterQueue(this);
        System.out.println(Thread.currentThread().getName()
                + " van thread is unloaded. Check load "
                + this.getVanLoad());
    }
}
