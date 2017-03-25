package ESC.Week8;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by HanWei on 16/3/2017.
 */
public class SafeStack {
    private final Object[] stack;
    private int pointer;
    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        int size = 1000000;
        int sampleSize = 20;
        SafeStack stack = new SafeStack(size);
        Object[] sampleObjects = new Object[size];

        for (int i = 0; i < size; i++) {
            sampleObjects[i] = new Object();
        }

        int averagePushSync = 0;
        int averagePopSync = 0;
        int averagePushLock = 0;
        int averagePopLock = 0;
        int averagePushSyncStack = 0;
        int averagePopSyncStack = 0;

        for (int i = 0; i < sampleSize; i++) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                stack.pushIfNotFull(sampleObjects[j]);
            }

            averagePushSync += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                stack.popIfNotEmpty();
            }

            averagePopSync += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                stack.pushIfNotFullLock(sampleObjects[j]);
            }

            averagePushLock += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                stack.popIfNotEmptyLock();
            }

            averagePopLock += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                stack.pushIfNotFullSyncStack(sampleObjects[j]);
            }

            averagePushSyncStack += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                stack.popIfNotEmptySyncStack();
            }

            averagePopSyncStack += System.currentTimeMillis() - start;
        }

        averagePushSync /= (double) sampleSize;
        averagePopSync /= (double) sampleSize;
        averagePushLock /= (double) sampleSize;
        averagePopLock /= (double) sampleSize;
        averagePushSyncStack /= (double) sampleSize;
        averagePopSyncStack /= (double) sampleSize;

        System.out.println("Average time taken for synchronized push: " + averagePushSync);
        System.out.println("Average time taken for synchronized pop: " + averagePopSync);
        System.out.println("Average time taken for locked push: " + averagePushLock);
        System.out.println("Average time taken for locked pop: " + averagePopLock);
        System.out.println("Average time taken for synchronized stack push: " + averagePushSyncStack);
        System.out.println("Average time taken for synchronized stack pop: " + averagePopSyncStack);
    }

    public SafeStack(int size) {
        assert size > 0;
        stack = new Object[size];
        pointer = -1;
    }

    public synchronized Object popIfNotEmpty() {
        if (pointer == -1) {
            System.out.println("No objects in stack.");
            return null;
        }

        return stack[pointer--];
    }

    public synchronized void pushIfNotFull(Object object) {
        if (pointer >= stack.length - 1) {
            System.out.println("Stack is full.");
            return;
        }
        stack[++pointer] = object;
    }

    public Object popIfNotEmptyLock() {
        lock.lock();
        if (pointer == -1) {
            System.out.println("No objects in stack.");
            return null;
        }

        Object result = stack[pointer--];
        lock.unlock();
        return result;
    }

    public void pushIfNotFullLock(Object object) {
        lock.lock();
        if (pointer >= stack.length - 1) {
            System.out.println("Stack is full.");
            return;
        }
        stack[++pointer] = object;
        lock.unlock();
    }

    public Object popIfNotEmptySyncStack() {
        synchronized (stack) {
            if (pointer == -1) {
                System.out.println("No objects in stack.");
                return null;
            }

            Object result = stack[pointer--];
            return result;
        }
    }

    public void pushIfNotFullSyncStack(Object object) {
        synchronized (stack) {
            if (pointer >= stack.length - 1) {
                System.out.println("Stack is full.");
                return;
            }
            stack[++pointer] = object;
        }
    }
}
