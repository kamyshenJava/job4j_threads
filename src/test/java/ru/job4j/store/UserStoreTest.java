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
        userStore.transfer(0, 1, 300);
        assertEquals(expected, first.getAmount());
    }

    @Test
    public void whenAddUserWithSameIdThenFalse() {
        User first = new User(0, 500);
        User second = new User(0, 100);
        UserStore userStore = new UserStore();
        userStore.add(first);
        assertFalse(userStore.add(second));
    }

}