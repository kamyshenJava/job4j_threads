package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String outputFilename = url.substring(url.lastIndexOf("/") + 1);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outputFilename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead = 1;
            while (bytesRead != -1) {
                long start = System.nanoTime();
                bytesRead = in.read(dataBuffer, 0, 1024);
                if (bytesRead != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                long finish = System.nanoTime();
                int timeElapsedMillis = (int) (finish - start) / 1000000;
                int timeToSleep = Math.max((1000 / speed) - timeElapsedMillis, 0);
                System.out.println(timeElapsedMillis);
                System.out.println(timeToSleep);
                Thread.sleep(timeToSleep);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
