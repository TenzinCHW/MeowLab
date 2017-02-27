package ESC.Week5;

import java.util.Arrays;

/**
 * Created by HanWei on 26/2/2017.
 */
public class MatrixThread {
    private int A[][];
    private int B[][];
    private int result[][];
    private Runnable colleagueThreads[];
    private Thread threads[];
    private int numberOfThreads;

    public static void main(String[] args) {
        int A[][] = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};
        int B[][] = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};

        MatrixThread meow = new MatrixThread(A, B, 2);
        meow.solve();
        for (int i = 0; i < A.length; i++) {
            System.out.println(Arrays.toString(meow.getResult()[i]));
        }
    }

    public MatrixThread(int[][] matrixA, int[][] matrixB, int numThreads) {
        A = matrixA;
        B = matrixB;
        numberOfThreads = numThreads;
        colleagueThreads = new ColleagueThread[numThreads];
        threads = new Thread[numThreads];
        assert (A.length > 0 && B.length > 0 && A[0].length > 0 && B[0].length > 0);
        result = new int[A.length][B[0].length];
    }

    public int[][] getResult() {
        return result;
    }

    public void solve() {
        splitArray();
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(colleagueThreads[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("A thread got interrupted.");
            }
        }
    }

    private void splitArray() {
        for (int i = 0; i < numberOfThreads; i++) {
            colleagueThreads[i] = new ColleagueThread(A, B, result, i * A.length / numberOfThreads, (i + 1) * A.length / numberOfThreads);
        }
    }

}

class ColleagueThread implements Runnable {
    private int A[][];
    private int B[][];
    private int result[][];
    private int start;
    private int end;


    public ColleagueThread(int[][] firstSection, int[][] secondSection, int[][] res, int start, int end) {
        A = firstSection;
        B = secondSection;
        result = res;
        this.start = start;
        this.end = end;
    }

    public void run() {
        multiply();
    }

    private void multiply() {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < B[i].length; j++) {
                multiplySingleRowCol(i, j);
            }
        }
    }

    private void multiplySingleRowCol(int rowA, int colB) {
        for (int i = 0; i < A[rowA].length; i++) {
            result[rowA][colB] += A[rowA][i] * B[i][colB];
        }
    }
}