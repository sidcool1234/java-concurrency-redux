package org.sid;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        for(int i=0;i<10000;i+=8){
            new Thread(new FindIfPrimesInRange(i, i+8)).start();
        }
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

class FindIfPrimesInRange implements Runnable {

    private final int start;
    private final int end;

    public FindIfPrimesInRange(int start, int end) {
        if(start >= end) throw new IllegalArgumentException("Start cannot be same or more than End value");
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        var ret = IntStream.rangeClosed(start, end).filter(FindIfPrimesInRange::isPrime).boxed().toList();
        System.out.println("Any Primes in the range - " + start + " and " + end + "? --> " + ret.size());
    }

    private static boolean isPrime(int number) {
        BigInteger bigInt = BigInteger.valueOf(number);
        return bigInt.isProbablePrime(100);
    }
}