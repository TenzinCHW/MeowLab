package ESC.Week3;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by HanWei on 8/2/2017.
 */
public class TicTacToe {
    private final boolean[][] X = new boolean[3][3];
    private final boolean[][] O = new boolean[3][3];
    private static final boolean o = true;
    private int numMoves;

    public TicTacToe() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                X[i][j] = false;
                O[i][j] = false;
            }
        }
        numMoves = 0;
    }

    public void startGame() {
        Scanner in = new Scanner(System.in);
        Random rng = new Random();
        final boolean first;  // true if O goes first, false if X goes first
        System.out.println("LET'S PLAY");
        if (rng.nextBoolean()) {
            System.out.println("O starts!");
            first = true;
        } else {
            System.out.println("X starts!");
            first = false;
        }

        while (!checkWin()) {
            if (makeMove(first, in)) {
                break;
            }

            if (makeMove(!first, in)) {
                break;
            }
        }
    }

    private boolean makeMove(boolean player, Scanner in) {
        String move;
        if (player) {
            System.out.println("O" + " move x (1 - 3), y (1 - 3):");
        } else {
            System.out.println("X" + " move x (1 - 3), y (1 - 3):");
        }
        move = in.nextLine();
        while (!parseAndPlace(move, player)) {
            System.out.println("Invalid play!");
            System.out.println("Please try again:");
            move = in.nextLine();
        }
        numMoves++;
        printBoard();
        if (checkWin()) {
            whoWon();
            return true;
        } else if (numMoves == 9) {
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }

    private boolean parseAndPlace(String move, boolean player) {
        if (move.matches("\\s*[1,2,3]\\s*,\\s*[1,2,3]\\s*")) {
            String[] moves = move.split(",");
            int i = Integer.parseInt(moves[0]) - 1;
            int j = 3- Integer.parseInt(moves[1]);
            return placePiece(j, i, player);
        }
        return false;
    }

    private boolean placePiece(int i, int j, boolean XO) {
        if (!X[i][j] && !O[i][j]) {
            if (XO) {
                O[i][j] = true;
            } else {
                X[i][j] = true;
            }
            return true;
        } else {
            System.out.println("There's already something there!");
            return false;
        }
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (X[i][j]) {
                    System.out.print("| X ");
                } else if (O[i][j]) {
                    System.out.print("| O ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
            if (i < 2) {
                System.out.println("-------------");
            }
        }
    }

    private boolean checkWin() {
        if (Won(o) || Won(!o)) {
            return true;
        }
        return false;
    }

    private void whoWon() {
        if (Won(o)) {
            System.out.println("O wins!");
        } else if (Won(!o)) {
            System.out.println("X wins!");
        }
    }

    private boolean Won(boolean XO) {
        return checkRows(XO) || checkCols(XO) || checkDiag(XO);
    }

    private boolean checkRows(boolean XO) { // XO is false for X, true for O
        for (int i = 0; i < 3; i++) {
            if (checkRow(i, XO)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRow(int row, boolean XO) {
        if (XO) {
            return O[row][0] && O[row][1] && O[row][2];
        }
        return X[row][0] && X[row][1] && X[row][2];
    }

    private boolean checkCols(boolean XO) {
        for (int i = 0; i < 3; i++) {
            if (checkCol(i, XO)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCol(int col, boolean XO) {
        if (XO) {
            return O[0][col] && O[1][col] && O[2][col];
        }
        return X[0][col] && X[1][col] && X[2][col];
    }

    private boolean checkDiag(boolean XO) {
        if (XO) {
            return (O[0][0] && O[1][1] && O[2][2]) || (O[0][2] && O[1][1] && O[2][0]);
        }
        return (X[0][0] && X[1][1] && X[2][2]) || (X[0][2] && X[1][1] && X[2][0]);
    }

}
