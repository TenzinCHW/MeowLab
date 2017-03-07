package ESC.Week6;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by HanWei on 7/3/2017.
 */
public class SynchronizedAccount {
    private int balance = 0;
    private Scanner in = new Scanner(System.in);
    public static int check = 0;
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        SynchronizedAccount account = new SynchronizedAccount();
        Runnable runs[] = new Runnable[10];
        Thread threads[] = new Thread[runs.length];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new workerWDDP(account);
            threads[i] = new Thread(runs[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("One or more threads could not finish.");
            }
        }
        System.out.println("Final balance: " + account.getBalance());
        System.out.println("Check balance: " + account.check);
    }

    public synchronized void withdraw(int amt) {
        if (balance >= amt)
            if (amt > 0) balance -= amt;
            else {
                System.out.println("Are you trying to do a deposit?");
                if (in.nextLine().matches("y|Y|yes|Yes|Yup|yup|yep|Yep|Yay|yay|yey|Yey")) {
                    System.out.println("THEN USE THE FREAKING DEPOSIT FUNCTION DAMMIT!");
                } else System.out.println("Well I can't withdraw a negative amount for you.");
            }
        else System.out.println("Not enuff mullah in your account.");
    }

    public synchronized void deposit(int amt) {
        if (amt > 0) balance += amt;
        else {
            System.out.println("Are you trying to do a withdraw?");
            if (in.nextLine().matches("y|Y|yes|Yes|Yup|yup|yep|Yep|Yay|yay|yey|Yey")) {
                System.out.println("THEN USE THE FREAKING WITHDRAW FUNCTION DAMMIT!");
            } else System.out.println("Well I can't deposit a negative amount for you.");
        }
    }

    public int getBalance() {
        return balance;
    }
}

class workerWDDP implements Runnable {
    private SynchronizedAccount acc;

    public workerWDDP(SynchronizedAccount account) {
        acc = account;
    }
    public void run() {
        playWithMoney();
    }

    private void playWithMoney() {
        Random random = new Random();
        int wd = random.nextInt(2000);
        System.out.println("Withdrawing " + wd);
        acc.withdraw(wd);
        SynchronizedAccount.lock.lock();
        try {
            if (wd > acc.getBalance()) ;
            else SynchronizedAccount.check -= wd;
        } finally {
            SynchronizedAccount.lock.unlock();
        }
        int dp = random.nextInt(2000);
        System.out.println("Depositing " + dp);
        acc.deposit(dp);
        SynchronizedAccount.lock.lock();
        try {
            SynchronizedAccount.check += dp;
        } finally {
            SynchronizedAccount.lock.unlock();
        }
        wd = random.nextInt(10000);
        System.out.println("Withdrawing negative amount: " + wd);
        acc.withdraw(-wd);
        dp = random.nextInt(10000);
        System.out.println("Depositing negative amount: " + dp);
        acc.deposit(-dp);
    }
}