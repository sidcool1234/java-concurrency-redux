package org.sid;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!, Main thread-" + Thread.currentThread().getName());

        for (int i = 0; i < 20; i++) {
            new Later().start();
        }
        System.out.println("Hello world!, Main thread Over");
    }
}

class Later extends Thread {
    @Override
    public void run() {
        System.out.println("Starting Thread Execution -- " + Thread.currentThread().getName());
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Finishing Thread Execution -- " + Thread.currentThread().getName() + " :: Time -- " + (System.currentTimeMillis() - time));
    }
}