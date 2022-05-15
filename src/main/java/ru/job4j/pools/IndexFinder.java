package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexFinder extends RecursiveTask<Integer> {

    private final Object[] array;
    private final Object target;
    private int from;
    private int to;

    public IndexFinder(Object[] array, Object target) {
        this.array = array;
        this.target = target;
    }

    public IndexFinder(Object[] array, Object target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        Integer rsl = null;
        to = to == 0 ? array.length - 1 : to;
        if (to - from < 11) {
            for (int i = from; i <= to; i++) {
                if (target.equals(array[i])) {
                    rsl = i;
                    break;
                }
            }
        } else {
            IndexFinder left =
                    new IndexFinder(array, target, from, (to - from) / 2 + from);
            IndexFinder right =
                    new IndexFinder(array, target,  from + (to - from) / 2 + 1, to);
            Integer l = left.invoke();
            rsl = l == null ? right.invoke() : l;
        }
        return rsl;
    }

    public static Integer find(Object[] array, Object target) {
        ForkJoinPool pool = new ForkJoinPool();
        IndexFinder indexFinder = new IndexFinder(array, target);
        return pool.invoke(indexFinder);
    }
}
