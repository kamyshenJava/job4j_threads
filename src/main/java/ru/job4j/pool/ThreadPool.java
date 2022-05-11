package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        tasks = new SimpleBlockingQueue<>(size + 1);
        createThreadPool(size);
    }

    private Thread createThread() {
        return new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable task = tasks.poll();
                    task.run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void createThreadPool(int size) {
        for (int i = 0; i < size; i++) {
            Thread thread = createThread();
            threads.add(thread);
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public synchronized void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
