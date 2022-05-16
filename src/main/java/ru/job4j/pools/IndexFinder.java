package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexFinder<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T target;
    private int from;
    private int to;

    public IndexFinder(T[] array, T target) {
        this.array = array;
        this.target = target;
        to = array.length - 1;
    }

    public IndexFinder(T[] array, T target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int rsl;
        int range = to - from;
        if (range < 11) {
            return search();
        }
        IndexFinder<T> left =
                new IndexFinder<>(array, target, from, range / 2 + from);
        IndexFinder<T> right =
                new IndexFinder<>(array, target,  from + range / 2 + 1, to);
        left.fork();
        right.fork();
        rsl = Math.max(left.join(), right.join());
        return rsl;
    }

    private int search() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (target.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public int find(T[] array, T target) {
        ForkJoinPool pool = new ForkJoinPool();
        IndexFinder<T> indexFinder = new IndexFinder<>(array, target);
        return pool.invoke(indexFinder);
    }
}
