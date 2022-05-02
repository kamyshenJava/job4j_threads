package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int flag = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String symbol = (flag % 2) == 0 ? "/" : "\\";
                System.out.print("\r load: " + symbol);
                flag++;
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
