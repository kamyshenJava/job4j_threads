package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            int id = model.getId();
            int version = model.getVersion();
            Base stored = memory.get(id);
            if (stored.getVersion() != version) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(id, version + 1);
        }) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId()) != null;
    }
}
