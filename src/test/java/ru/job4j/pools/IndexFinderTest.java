package ru.job4j.pools;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class IndexFinderTest {

    private static class Obj {
        int index;
        String name;

        public Obj(int index, String name) {
            this.index = index;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Obj obj = (Obj) o;
            return index == obj.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }
    }

    @Test
    public void findIndexThen31() {
        List<Obj> temp = new ArrayList<>();
        Obj target = new Obj(31, "b");
        IntStream.range(0, 50).forEach(x -> temp.add(new Obj(x, "a")));
        Obj[] arr = temp.toArray(new Obj[0]);
        IndexFinder<Obj> indexFinder = new IndexFinder<>(arr, target);
        int expected = 31;
        assertThat(expected, is(indexFinder.find(arr, target)));
    }

    @Test
    public void findIndexThen17() {
        List<Obj> temp = new ArrayList<>();
        Obj target = new Obj(17, "b");
        IntStream.range(0, 50).forEach(x -> temp.add(new Obj(x, "a")));
        Obj[] arr = temp.toArray(new Obj[0]);
        IndexFinder<Obj> indexFinder = new IndexFinder<>(arr, target);
        int expected = 17;
        assertThat(expected, is(indexFinder.find(arr, target)));
    }
}