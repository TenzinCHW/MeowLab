package ESC.Week9;

import java.util.concurrent.CountDownLatch;

/**
 * Created by HanWei on 26/3/2017.
 * <p>
 * This assumes that the array ALWAYS has at least the required number of F's specified by numberOfF.
 */
public class CountDownLatchExercise {
    public static void main(String[] args) {
        int numberOfF = 7;
        int numThread = 20;
        CountDownLatch latch = new CountDownLatch(numberOfF);
        String[] arr = {"A", "B", "D", "E", "F", "C", "F", "F", "A", "F", "C", "E", "B", "B", "F", "D", "E", "A", "F", "F"};
        Thread[] threads = new Thread[numThread];
        int start;
        int end;
        int interval = (int) Math.ceil(((double) arr.length) / numThread);
        for (int i = 0; i < numThread; i++) {
            start = i * interval;
            end = (i + 1) * interval;
            if (start > arr.length) threads[i] = new countF(arr, latch, 0, 0);
            else if (end > arr.length) threads[i] = new countF(arr, latch, start, arr.length);
            else threads[i] = new countF(arr, latch, start, end);
        }

        for (int i = 0; i < numThread; i++) {
            threads[i].start();
        }

        try {
            latch.await();
            System.out.println("Found " + (numberOfF - latch.getCount()) + " F's!");
            for (int i = 0; i < numThread; i++) {
                threads[i].interrupt();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

class countF extends Thread {
    CountDownLatch latch;
    String[] arr;
    int startIndex;
    int endIndex;

    public countF(String[] array, CountDownLatch lat, int start, int end) {
        arr = array;
        latch = lat;
        startIndex = start;
        endIndex = end;
    }

    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (interrupted()) {
                System.out.println("Exiting");
                break;
            }
            if (arr[i].equals("F")) {
                latch.countDown();
                System.out.println("Found an F!");
            }
        }
    }
}