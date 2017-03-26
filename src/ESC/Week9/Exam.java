package ESC.Week9;

import java.util.concurrent.Phaser;

/**
 * Created by HanWei on 26/3/2017.
 */
public class Exam {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        phaser.register();
        Student meow = new Student("Meow", phaser, 387, 1000);
        Student ruff = new Student("Ruff", phaser, 5235, 34);
        Student rawr = new Student("Rawr", phaser, 32, 9507);
        Student gruff = new Student("Gruff", phaser, 548, 2345);
        meow.takeExam();
        ruff.takeExam();
        rawr.takeExam();
        gruff.takeExam();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Examiner is ready to start the exam.");
        phaser.arriveAndAwaitAdvance();
        System.out.println("Exam is now over. Examiner is leaving the exam hall.");
    }
}

class Student {
    private int timeToStart;
    private int timeToComplete;
    private Phaser phaser;
    private String name;

    public Student(String name, Phaser phaser, int start, int complete) {
        this.name = name;
        this.phaser = phaser;
        timeToStart = start;
        timeToComplete = complete;
    }

    public void takeExam() {
        phaser.register();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeToStart);
                    System.out.println(name + " is ready to take the exam.");
                    phaser.arriveAndAwaitAdvance();
                    Thread.sleep(timeToComplete);
                } catch (InterruptedException e) {
                    System.out.println(name + " was not able to finish the exam.");
                } finally {
                    phaser.arriveAndDeregister();
                }
                System.out.println(name + " has finished exam. Handed in and leaving now.");
            }
        }.start();
    }
}