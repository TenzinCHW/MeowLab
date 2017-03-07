package ESC.Week6;

/**
 * Created by HanWei on 6/3/2017.
 */
public class MultiThreadSearch {
    public static void main(String[] args) {
        int toSearchIn[] = {1,5,2,4,6,8,3,7,10,9,49,23,13,34,95,54,84,79,26,40,57,62,91,53};
        int num = 34;
        Searcher searcher = new Searcher(toSearchIn, 2, num);
        searcher.startAllThreads();
    }
}

class Searcher extends Thread {
    private int arr[];
    private int start;
    private int len;
    private int search;
    private Thread threads[];
    public boolean found = false;

    public Searcher(int array[], int numThreads, int searchNum) {
        arr = array;
        len = array.length/numThreads;
        this.start = 0;
        this.search = searchNum;

        if (numThreads > 1) {
            threads = new Thread[numThreads];
            for (int i = 0; i < numThreads - 1; i++) {
                threads[i] = new Searcher(array, searchNum, (i+1) * len, len, threads);
            }
            threads[numThreads-1] = this;
        }
    }

    public Searcher(int array[], int searchNum, int start, int length, Thread thread[]) {
        arr = array;
        search = searchNum;
        this.start = start;
        len = length;
        threads = thread;
    }

    public void run() {
        try {
            for (int i = start; i < start + len; i++) {
                if (arr[i] == search) {
                    found = true;
                    System.out.println("Found " + search + " at " + "position " + i + ".");
                    stopAllThreads();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Searched outside bounds but that's ok. Ending now.");
        }
    }

    public void startAllThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void stopAllThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].interrupt();
        }
    }
}