package ESC.Week6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by HanWei on 7/3/2017.
 */
public class LockStaticVariablesFixed {
    public static int saving = 5000;
    public static int cash = 0;

    public static void main(String args[]) {
        int numberofThreads = 10000;
        WD1[] threads = new WD1[numberofThreads];

        for (int i = 0; i < numberofThreads; i++) {
            threads[i] = new WD1();
            threads[i].start();
        }

        try {
            for (int i = 0; i < numberofThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("some thread is not finished");
        }

        System.out.print("The result is: " + LockStaticVariablesFixed.cash);
    }
}

class WD1 extends Thread {
    private static Lock lock = new ReentrantLock();
    public void run () {
        lock.lock();
//        assert (LockStaticVariablesFixed.cash + LockStaticVariablesFixed.saving == 5000);
        try {
            if (LockStaticVariablesFixed.saving >= 1000) {
                System.out.println("I am doing something.");
                LockStaticVariablesFixed.saving = LockStaticVariablesFixed.saving - 1000;
                LockStaticVariablesFixed.cash = LockStaticVariablesFixed.cash + 1000;
            }
        } finally {
            lock.unlock();
        }
    }
}