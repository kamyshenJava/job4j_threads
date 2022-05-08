package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddThenTrue() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        assertTrue(cache.add(first));
    }

    @Test
    public void whenUpdateWithSameVersionThenTrue() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        first.setName("first");
        Base second = new Base(1, 1);
        second.setName("second");
        cache.add(first);
        assertTrue(cache.update(second));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateWithDifferentVersionThenException() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        first.setName("first");
        Base second = new Base(1, 2);
        second.setName("second");
        cache.add(first);
        cache.update(second);
    }

    @Test
    public void whenAddAndDeleteThenTrue() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        cache.add(first);
        assertTrue(cache.delete(first));
    }

}