package ESC.Week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by HanWei on 27/2/2017.
 */
public class TicTacToePlayer {
    private Socket connection;
    private PrintWriter writer;
    private BufferedReader reader;
    private Scanner in;

    public static void main(String[] args) {
        TicTacToePlayer player = new TicTacToePlayer();
        player.startClient();
    }

    public TicTacToePlayer() {
        try {
            connection = new Socket();
            InetSocketAddress socketAddress = new InetSocketAddress("localhost", 5432);
            connection.connect(socketAddress, 100);
        } catch (IOException e) {
            System.out.println("Could not connect :(.");
        }
    }

    public void startClient() {
        try {
            writer = new PrintWriter(connection.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new Scanner(System.in);

            String serverMsg;
            String input;
            while (true) {
                serverMsg = receiveMsg();
                input = parseMsg(serverMsg);
                if (input == null) break;
                else if (input.equals("Just print")) System.out.println(serverMsg);
                else {
                    sendMsg(input);
                }
            }
        } catch (IOException e) {
            System.exit(-1);
        }
        disconnect();
    }

    private String receiveMsg() {
        String input;
        try {
            while ((input = reader.readLine()) != null) {
                return input;
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server :(.");
            System.exit(-1);
        }
        return "Nothing sent by server.";
    }

    private void sendMsg(String msg) {
        writer.println(msg);
        writer.flush();
    }

    /* returns true if game has ended */
    private String parseMsg(String msg) {
        if (msg.endsWith("wins!") || msg.endsWith("draw!")) {
            System.out.println(msg);
            return null;
        }
        else if (msg.endsWith(":")) {
            System.out.println(msg);
            String input = in.nextLine();
            return input;
        }
        return "Just print";
    }

    private void disconnect() {
        try {
            writer.close();
            reader.close();
            connection.close();
            in.close();
        } catch (IOException e) {
            System.out.println("Connection is already closed.");
        }
    }
}
