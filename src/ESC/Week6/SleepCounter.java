package ESC.Week6;

import java.util.Scanner;

/**
 * Created by HanWei on 7/3/2017.
 */
public class SleepCounter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Counter counter = new Counter();
        counter.start();
        String line;

        while (true) {
            line = in.nextLine();
            if (line.equals("0")) {
                counter.interrupt();
                break;
            }
        }

    }
}

class Counter extends Thread {
    long count;

    public Counter() {
        count = 0;
    }

    public void run() {
        startCounting();
    }

    private void startCounting() {
        while (!this.isInterrupted()) {
            try {
                System.out.println(count);
                count++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Ok stopped counting.");
                return;
            }
        }
    }

}