package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@ThreadSafe
public final class UserStore {

    @GuardedBy("this")
    private final List<User> store = new ArrayList<>();

    public synchronized boolean add(User user) {
        return store.add(user);
    }

    public synchronized boolean update(User user) {
        ListIterator<User> li = store.listIterator();
        int id = user.getId();
        boolean rsl = false;
        while (li.hasNext()) {
            if (li.next().getId() == id) {
                rsl = true;
                li.set(user);
                break;
            }
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        int size = store.size();
        boolean rsl = (fromId < size && fromId >= 0
                && toId < size && toId >= 0 && fromId != toId
                && store.get(fromId).getAmount() >= amount);
        if (rsl) {
            User from = store.get(fromId);
            User to = store.get(toId);
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
        }
        return rsl;
    }
}
