package ESC.Week12;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class PerformanceExperiment {
    private static final int NUM_THREADS = 20; // ~ 3.5 times speedup.
    private static final int numTrials = 10;
    //private static BigInteger n = new BigInteger("1127451830576035879");
    //private static BigInteger n = new BigInteger("4294967297");
    private static BigInteger n = new BigInteger("239839672845043");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= NUM_THREADS; i++) {
            BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);

            for (int j = 0; j < 10; j++) {
                queue.add(new Runnable() {
                    public void run() {
                        factor(n);
                    }
                });
            }
            int totalTime = 0;
            for (int j = 0; j < numTrials; j++) {
                Thread[] threads = new Thread[i];

                long startTime = System.currentTimeMillis();
                for (int k = 0; k < i; k++) {
                    threads[k] = new Slave(queue);
                    threads[k].start();
                }

                for (int k = 0; k < i; k++) {
                    threads[k].join();
                }
                long endTime = System.currentTimeMillis();
                totalTime += (endTime - startTime);
            }
            System.out.println("Time spent for " + i + " threads: " + totalTime / numTrials);
        }
    }

    public static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");
        BigInteger zero = new BigInteger("0");

        while (i.compareTo(n) < 0) {
            //System.out.println("trying" + i);
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }

            i = i.add(new BigInteger("1"));
        }

        assert (false);
        return null;
    }
}

class Slave extends Thread {
    private final BlockingQueue<Runnable> queue;

    public Slave(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Runnable task = queue.poll();
                task.run();
            } catch (Exception e) {
                break;
            }
        }
    }
}