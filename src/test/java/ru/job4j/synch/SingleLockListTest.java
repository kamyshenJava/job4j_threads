package ru.job4j.synch;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> l = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(l);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void whenIterateAndUpdateStorageThenConcurrentModificationException() {

        SingleLockList<Integer> singleLockList = new SingleLockList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> singleLockListIterator = singleLockList.iterator();
        System.out.println(singleLockListIterator.next());
        singleLockList.add(6);
        System.out.println(singleLockListIterator.next());
    }
}