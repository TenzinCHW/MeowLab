package ESC.Week5;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by HanWei on 26/2/2017.
 */
public class MatrixThreadArray {
    private int A[][];
    private int B[][];
    private int result[][];
    private Runnable colleagueThreads[];
    private int numberOfThreads;

    public static void main(String[] args) {
        int testThreads = 10;
        int arrays[] = {1, 2, 10, 50, 100, 500, 1000};
        ArrayList<String> timings = new ArrayList<>();
        Random rng = new Random();
        System.out.println("Please wait for me to do your work, human.");

        for (int thread = 0; thread < testThreads; thread++) {
            for (int arrSz :
                    arrays) {
                int A[][] = new int[arrSz][arrSz];
                int B[][] = new int[arrSz][arrSz];
                for (int i = 0; i < arrSz; i++) {
                    for (int j = 0; j < arrSz; j++) {
                        A[i][j] = rng.nextInt(100);
                        B[i][j] = rng.nextInt(100);
                    }
                }
                MatrixThreadArray startMe = new MatrixThreadArray(A, B, thread);
                long startTime = System.currentTimeMillis();
                startMe.solve();
                long endTime = System.currentTimeMillis();
                timings.add("The time taken to calculate for " + arrSz + " using " + (thread + 1)
                        + " threads is " + (endTime - startTime) + " milliseconds.");
            }
        }

        for (String time :
                timings) {
            System.out.println(time);
        }
    }

    public MatrixThreadArray(int[][] matrixA, int[][] matrixB, int numThreads) {
        A = matrixA;
        B = matrixB;
        numberOfThreads = numThreads;
        colleagueThreads = new ColleagueThread[numThreads];
        assert (A.length > 0 && B.length > 0 && A[0].length > 0 && B[0].length > 0);
        result = new int[A.length][B[0].length];
    }

    public int[][] getResult() {
        return result;
    }

    public void solve() {
        splitArray();
        for (Runnable thread :
                colleagueThreads) {
            thread.run();
        }
    }

    private void splitArray() {
        for (int i = 0; i < numberOfThreads; i++) {
            colleagueThreads[i] = new ColleagueThread(A, B, result, i * A.length / numberOfThreads, (i + 1) * A.length / numberOfThreads);
        }
    }
}
