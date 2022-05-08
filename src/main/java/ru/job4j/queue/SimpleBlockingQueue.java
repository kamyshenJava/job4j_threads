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

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
            this.wait();
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        T rsl = queue.poll();
        this.notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }
}
