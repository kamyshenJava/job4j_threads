package ru.job4j.store;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStoreTest {

    @Test
    public void whenTransfer300Then200() {
        User first = new User(0, 500);
        User second = new User(1, 100);
        UserStore userStore = new UserStore();
        userStore.add(first);
        userStore.add(second);
        int expected = 200;
        assertTrue(userStore.transfer(0, 1, 300));
        assertEquals(expected, first.getAmount());
    }

    @Test
    public void whenAddUserWithSameIdThenFalse() {
        User first = new User(0, 500);
        User second = new User(0, 100);
        UserStore userStore = new UserStore();
        assertTrue(userStore.add(first));
        assertFalse(userStore.add(second));
    }

    @Test
    public void whenUpdate() {
        User first = new User(0, 500);
        User second = new User(0, 100);
        UserStore userStore = new UserStore();
        userStore.add(first);
        int expected = 100;
        assertTrue(userStore.update(second));
        assertEquals(expected, userStore.get(0).getAmount());
    }

    @Test
    public void whenNotUpdate() {
        User first = new User(0, 500);
        User second = new User(1, 100);
        UserStore userStore = new UserStore();
        userStore.add(first);
        assertFalse(userStore.update(second));
    }

    @Test
    public void whenDelete() {
        User first = new User(0, 500);
        UserStore userStore = new UserStore();
        userStore.add(first);
        assertTrue(userStore.delete(first));
        assertNull(userStore.get(0));
    }
}