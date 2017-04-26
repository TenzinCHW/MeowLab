package ESC.Week11;

import java.util.Random;

/**
 * Created by HanWei on 8/4/2017.
 */
public class DiningPhil2 {
    private static int N = 5;

    public static void main(String[] args) throws Exception {
        Philosopher2[] phils = new Philosopher2[N];
        Fork[] forks = new Fork[N];

        for (int i = 0; i < N; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < N; i++) {
            phils[i] = new Philosopher2(i, forks[i], forks[(i + N - 1) % N]);
            phils[i].start();
        }
    }
}

class Philosopher2 extends Thread {
    private final int index;
    private final Fork left;
    private final Fork right;

    public Philosopher2(int index, Fork left, Fork right) {
        this.index = index;
        this.left = left;
        this.right = right;
    }

    public void run() {
        try {
            while (true) {
                if (index == 0) {
                    PhiloEat(true);
                } else {
                    PhiloEat(false);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Don't disturb me while I am sleeping, well, thinking.");
        }
    }

    public void PhiloEat(boolean leftOrRight) throws InterruptedException {
        Random randomGenerator = new Random();
        Thread.sleep(randomGenerator.nextInt(1000)); //not sleeping but thinking
        System.out.println("Phil " + index + " finishes thinking.");
        if (leftOrRight) {
            left.pickup();
            System.out.println("Phil " + index + " picks up left fork.");
            right.pickup();
            System.out.println("Phil " + index + " picks up right fork.");
        } else {
            right.pickup();
            System.out.println("Phil " + index + " picks up right fork.");
            left.pickup();
            System.out.println("Phil " + index + " picks up left fork.");
        }
        Thread.sleep(randomGenerator.nextInt(1000)); //eating
        System.out.println("Phil " + index + " finishes eating.");
        left.putdown();
        System.out.println("Phil " + index + " puts down left fork.");
        right.putdown();
        System.out.println("Phil " + index + " puts down right fork.");
    }
}
