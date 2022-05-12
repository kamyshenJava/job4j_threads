package ru.job4j.pools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexFinder extends RecursiveTask<List<Integer>> {

    private final Object[] array;
    private final Object target;
    private int from;

    public IndexFinder(Object[] array, Object target) {
        this.array = array;
        this.target = target;
    }

    public IndexFinder(Object[] array, Object target, int from) {
        this.array = array;
        this.target = target;
        this.from = from;
    }

    @Override
    protected List<Integer> compute() {
        List<Integer> rsl = new ArrayList<>();
        if (array.length < 11) {
            for (int i = 0; i < array.length; i++) {
                if (target.equals(array[i])) {
                    rsl.add(i + from);
                }
            }
        } else {
            IndexFinder left =
                    new IndexFinder(Arrays.copyOfRange(array, 0, array.length / 2), target, from);
            IndexFinder right =
                    new IndexFinder(Arrays.copyOfRange(array, array.length / 2,
                            array.length), target,  from + array.length / 2);
            rsl.addAll(left.invoke());
            rsl.addAll(right.invoke());
        }
        return rsl;
    }

    public static List<Integer> find(Object[] array, Object target) {
        ForkJoinPool pool = new ForkJoinPool();
        IndexFinder indexFinder = new IndexFinder(array, target);
        return pool.invoke(indexFinder);
    }
}
