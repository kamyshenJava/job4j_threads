package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        while (queue.size() >= limit) {
            try {
                this.wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() {
        while (queue.peek() == null) {
            try {
                this.wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        T rsl = queue.poll();
        this.notifyAll();
        return rsl;
    }
}
