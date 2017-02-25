package CSE.Week5;

import java.util.concurrent.Semaphore;

/**
 * Created by HanWei on 23/2/2017.
 */
public class ProdConsume {
    int in[];
    int count = 0;
    Producer producer;
    Semaphore mutex = new Semaphore(1);
    Semaphore empty;
    Semaphore full = new Semaphore(0);

    public ProdConsume(int n) {
        in = new int[n];
        empty = new Semaphore(n);
        producer = new Producer(mutex, empty, full, in);
    }

    public void consume() {
        try {
            mutex.acquire();
            full.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        in[count - 1] = 0;
        count--;

        mutex.release();
        empty.release();
    }

    public static void main(String[] args) {
        ProdConsume test = new ProdConsume(4);

//        Thread consumer = new Thread();


    }
}

class Producer implements Runnable{
    Semaphore mutex;
    Semaphore empty;
    Semaphore full;
    int in[];

    public Producer(Semaphore mut, Semaphore emp, Semaphore full, int[] array) {
        mutex = mut;
        empty = emp;
        this.full = full;
        in = array;
    }

    public void run() {
        produce(1, 0); // TODO wrong
    }

    public void produce(int item, int count) {
        try {
            mutex.acquire();
            empty.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        in[count] = item;
        count++;

        mutex.release();
        full.release();
    }
}