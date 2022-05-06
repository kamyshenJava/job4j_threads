package ru.job4j.queue;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenOfferAndPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> {
            sbq.offer(1);
        });
        Thread consumer = new Thread(() -> {
            int expected = 1;
            assertThat(expected, is(sbq.poll()));
        });
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
    }

    @Test
    public void whenOfferOverLimitThenWait() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        Thread producer1 = new Thread(() -> {
            sbq.offer(1);
        });
        Thread producer2 = new Thread(() -> {
            sbq.offer(2);
        });
        Thread consumer = new Thread(() -> {
            sbq.poll();
        });
        producer1.start();
        producer1.join();
        producer2.start();
        Thread.sleep(100);
        assertEquals("WAITING", producer2.getState().toString());
        consumer.start();
        consumer.join();
        producer2.join();
        assertEquals("TERMINATED", producer2.getState().toString());
    }

    @Test
    public void whenPollFromEmptyThenWait() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> {
            sbq.offer(1);
        });
        Thread consumer = new Thread(() -> {
            sbq.poll();
        });
        consumer.start();
        Thread.sleep(100);
        assertEquals("WAITING", consumer.getState().toString());
        producer.start();
        producer.join();
        consumer.join();
        assertEquals("TERMINATED", consumer.getState().toString());
    }

}