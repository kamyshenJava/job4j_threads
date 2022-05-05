package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public final class UserStore {

    @GuardedBy("this")
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return store.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User from = store.get(fromId);
        User to = store.get(toId);
        boolean condition = (from != null && to != null && fromId != toId
                && store.get(fromId).getAmount() >= amount);
        if (condition) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            rsl = true;
        }
        return rsl;
    }
}
