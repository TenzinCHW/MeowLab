package ESC.Week6;

/**
 * Created by HanWei on 7/3/2017.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FactorizerUserFixed {
    public static void main (String[] args) {
        CachedFactorizerThreadSafe factorizer = new CachedFactorizerThreadSafe ();
        Thread tr1 = new Thread (new MyRunnableFixed(factorizer));
        Thread tr2 = new Thread (new MyRunnableFixed(factorizer));
        tr1.start();
        tr2.start();

        try {
            tr1.join();
            tr2.join();
        }
        catch (Exception e) {

        }
    }
}

class MyRunnableFixed implements Runnable {
    private CachedFactorizerThreadSafe factorizer;

    public MyRunnableFixed (CachedFactorizerThreadSafe factorizer) {
        this.factorizer = factorizer;
    }

    public void run () {
        Random random = new Random ();
        int ran = random.nextInt(100);
        System.out.println("Factorizing " + ran);
        factorizer.service(ran);
    }
}

public class CachedFactorizerThreadSafe {
    private int lastNumber;
    private List<Integer> lastFactors;
    private long hits;
    private long cacheHits;
    Lock lock = new ReentrantLock();

    public long getHits () {
        return hits;
    }

    public double getCacheHitRatio () {
        return (double) cacheHits/ (double) hits;
    }

    public List<Integer> service (int input) {
        List<Integer> factors = null;
        ++hits;

        lock.lock();
        try {
            if (input == lastNumber) {
                System.out.println("HIT");
                ++cacheHits;
                factors = new ArrayList<Integer>(lastFactors);
            }

            if (factors == null) {
                System.out.println("No hit.");
                factors = factor(input);
                lastNumber = input;
                lastFactors = new ArrayList<Integer>(factors);
            }
        } finally {
            lock.unlock();
        }

        return factors;
    }

    public List<Integer> factor(int n) {
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }

        return factors;
    }
}