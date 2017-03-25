package ESC.Week8;

import java.util.Random;

public class BufferExample {
    public static void main(String[] args) throws Exception {
        Buffer buffer = new Buffer(10);
        MyProducer prod = new MyProducer(buffer);
        prod.start();
        MyConsumer cons = new MyConsumer(buffer);
        cons.start();
    }
}

class MyProducer extends Thread {
    private Buffer buffer;

    public MyProducer(Buffer buffer) {
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

class MyConsumer extends Thread {
    private Buffer buffer;

    public MyConsumer(Buffer buffer) {
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

class Buffer {
    public int SIZE;
    private Object[] objects;
    private int count = 0;

    public Buffer(int size) {
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