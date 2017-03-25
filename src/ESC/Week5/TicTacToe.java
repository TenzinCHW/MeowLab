package ESC.Week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by HanWei on 27/2/2017.
 */
public class TicTacToe {
    private ServerSocket serverSocket;
    private ArrayList<Socket> players;
    private ArrayList<PrintWriter> writers;
    private ArrayList<BufferedReader> readers;
    private final boolean[][] X = new boolean[3][3];
    private final boolean[][] O = new boolean[3][3];
    private static final boolean o = true;
    private int numMoves;

    public static void main(String[] args) {
        TicTacToe newGame = new TicTacToe();
        newGame.startGame();
    }

    public TicTacToe() {
        players = new ArrayList<>();
        writers = new ArrayList<>();
        readers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                X[i][j] = false;
                O[i][j] = false;
            }
        }
        numMoves = 0;
    }

    public void startGame() {
        try {
            serverSocket = new ServerSocket(5432);
            System.out.println("Waiting for first player...");
            Socket firstPlayer = serverSocket.accept();
            players.add(firstPlayer);
            System.out.println("First player connected, now waiting for second player...");
            Socket secondPlayer = serverSocket.accept();
            players.add(secondPlayer);
            System.out.println("Second player connected. Let's play!");
            for (int i = 0; i < 2; i++) {
                writers.add(new PrintWriter(players.get(i).getOutputStream(), true));
                readers.add(new BufferedReader(new InputStreamReader(players.get(i).getInputStream())));
            }
        } catch (IOException e) {
            System.out.println("Someone failed to connect :(.");
        }

        Random rng = new Random();
        final boolean first;  // true if O goes first, false if X goes first
        notifyPlayers("LET'S PLAY");
        if (rng.nextBoolean()) {
            notifyPlayers("O starts!");
            first = true;
        } else {
            notifyPlayers("X starts!");
            first = false;
        }

        while (!checkWin()) {
            if (makeMove(first)) {
                break;
            }

            if (makeMove(!first)) {
                break;
            }
        }

        closeConnections();
    }

    private boolean makeMove(boolean player) {
        updatePlayer(player, "Your turn!");
        if (player) {
            System.out.println("O" + " move x (1 - 3), y (1 - 3):");
            updatePlayer(player, "O" + " move x (1 - 3), y (1 - 3):");
        } else {
            System.out.println("X" + " move x (1 - 3), y (1 - 3):");
            updatePlayer(player, "X" + " move x (1 - 3), y (1 - 3):");
        }
        String move = getPlayerMove(player);
        while (!parseAndPlace(move, player)) {
            System.out.println("Invalid play!");
            System.out.println("Please try again:");
            updatePlayer(player, "Invalid play!");
            updatePlayer(player, "Please try again:");
            move = getPlayerMove(player);
        }
        numMoves++;

        if (player) {
            updatePlayer(!player, "O moved!");
        } else {
            updatePlayer(!player, "X moved!");
        }
        printBoard();
        if (checkWin()) {
            whoWon();
            return true;
        } else if (numMoves == 9) {
            notifyPlayers("It's a draw!");
            return true;
        }
        return false;
    }

    private void updatePlayer(boolean player, String message) {
        if (player) {
            writers.get(0).println(message);
        } else {
            writers.get(1).println(message);
        }
    }

    private void notifyPlayers(String message) {
        System.out.println(message);
        for (int i = 0; i < 2; i++) {
            writers.get(i).println(message);
        }
    }

    private String getPlayerMove(boolean player) {
        String playerInput = null;
        try {
            if (player) {
                playerInput = readers.get(0).readLine();
                return playerInput;
            } else {
                playerInput = readers.get(1).readLine();
                return playerInput;
            }
        } catch (
                IOException e)

        {
            System.out.println("Someone disconnected...");
            System.exit(-1);
        }
        return playerInput;
    }

    private boolean parseAndPlace(String move, boolean player) {
        if (move.matches("\\s*[1,2,3]\\s*,\\s*[1,2,3]\\s*")) {
            String[] moves = move.split(",");
            int i = Integer.parseInt(moves[0]) - 1;
            int j = 3 - Integer.parseInt(moves[1]);
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
            updatePlayer(XO, "There's already something there!");
            return false;
        }
    }

    private void printBoard() {
        String board = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (X[i][j]) {
                    board += "| X ";
                } else if (O[i][j]) {
                    board += "| O ";
                } else {
                    board += "|   ";
                }
            }
            board += "|";
            if (i < 2) {
                board += "\n-------------\n";
            }
        }
        notifyPlayers(board);
    }

    private boolean checkWin() {
        return Won(o) || Won(!o);
    }

    private void whoWon() {
        if (Won(o)) {
            notifyPlayers("O wins!");
        } else if (Won(!o)) {
            notifyPlayers("X wins!");
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

    private void closeConnections() {
        try {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).close();
                writers.get(i).close();
                readers.get(i).close();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("A client connection is already closed.");
        }
    }
}
