package ESC.Week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HanWei on 26/2/2017.
 */
public class ChatRoom {
    private ServerSocket serverSocket;
    private ArrayList<Socket> clients;
    private ArrayList<PrintWriter> writers;
    private ArrayList<BufferedReader> readers;
    private BufferedReader stdIn;
    private ArrayList<String> names;

    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.startServer();
    }

    public ChatRoom() {
        clients = new ArrayList<>();
        writers = new ArrayList<>();
        readers = new ArrayList<>();
        stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        names = new ArrayList<>();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(4321);
            serverSocket.setSoTimeout(100);
        } catch (Exception e) {
            System.out.println("Could not start server. Port is being used (most likely).");
            return;
        }

        while (true) {
            checkForNewClient();
            checkForNewMessage();
        }
    }

    private void checkForNewClient() {
        try {
            Socket newClient = serverSocket.accept();
            clients.add(newClient);
            newClient.setSoTimeout(100);
            writers.add(
                    new PrintWriter(newClient.getOutputStream(), true));
            readers.add(new BufferedReader(
                    new InputStreamReader(newClient.getInputStream())));
            String name = readers.get(readers.size() - 1).readLine();
            names.add(name);
            System.out.println(name + " has just joined the chat.");
        } catch (Exception e) {
            return;
        }
    }

    private void checkForNewMessage() {
        String inputLine;
        for (int i = 0; i < clients.size(); i++) {
            try {
                inputLine = readers.get(i).readLine();
                System.out.println(names.get(i) + ": " + inputLine);
            } catch (Exception e) {
                return;
            }
        }
    }
}
