package ESC.Week8;

import java.util.Random;

/**
 * Created by HanWei on 16/3/2017.
 */
public class BufferFixed {
    public static void main(String[] args) throws Exception {
        Buffer2 buffer = new Buffer2(10);
        MyProducerFixed prod = new MyProducerFixed(buffer);
        prod.start();
        MyConsumerFixed cons = new MyConsumerFixed(buffer);
        cons.start();
    }
}

class MyProducerFixed extends Thread {
    private Buffer2 buffer;

    public MyProducerFixed(Buffer2 buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buffer.addItem(new Object());
    }
}

class MyConsumerFixed extends Thread {
    private Buffer2 buffer;

    public MyConsumerFixed(Buffer2 buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buffer.removeItem();
    }
}

class Buffer2 {
    public int SIZE;
    private Object[] objects;
    private int count = 0;

    public Buffer2(int size) {
        SIZE = size;
        objects = new Object[SIZE];
    }

    public synchronized void addItem(Object object) {
        while (!(count < SIZE)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("Got interrupted.");
            }
        }
        objects[count] = object;
        count++;
        this.notifyAll();
        System.out.println("Adding item.");
    }

    public synchronized Object removeItem() {
        while (!(count > 0)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("Got interrupted.");
            }
        }
        count--;
        System.out.println("Removing item.");
        this.notifyAll();
        return objects[count];
    }
}