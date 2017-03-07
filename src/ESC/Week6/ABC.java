package ESC.Week6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by HanWei on 7/3/2017.
 */
public class ABC {
    static Lock lock;
    public static void main(String[] args) {
        lock = new ReentrantLock();
        StringBuffer buffer = new StringBuffer();
        Thread one = new ABCWorker(buffer);
        Thread two = new ABCWorker(buffer);
        Thread three = new ABCWorker(buffer);
        one.start();
        two.start();
        three.start();
        try {
            one.join();
            two.join();
            three.join();
        } catch (InterruptedException e) {
            System.out.println("Someone couldn't finish.");
        }

        System.out.println(buffer);
    }
}

class ABCWorker extends Thread {
    StringBuffer buff;
    static char letter;

    public ABCWorker(StringBuffer buffer) {
        buff = buffer;
    }

    public void run() {
        justDoIt();
    }

    public void justDoIt() {
        ABC.lock.lock();
        try {
            if (buff.length() == 0) letter = 'A';
            else if (letter == 'A') letter = 'B';
            else if (letter == 'B') letter = 'C';

            for (int i = 0; i < 100; i++) {
                buff.append(letter);
            }
        } finally {
            ABC.lock.unlock();
        }
    }
}