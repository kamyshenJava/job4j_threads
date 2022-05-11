package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String username = user.username;
            String email = user.email;
            String subject = String.format("Notification %s to email %s", username, email);
            String body = String.format("Add a new event to %s", username);
            send(subject, body, email);
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) { }

    private static class User {
        private String username;
        private String email;

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}
