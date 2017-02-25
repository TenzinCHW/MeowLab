package ESC.Week5;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4321);
        ArrayList<Socket> clients = new ArrayList<>();
        ArrayList<PrintWriter> writers = new ArrayList<>();
        ArrayList<BufferedReader> readers = new ArrayList<>();
        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        ArrayList<Boolean> connected = new ArrayList<>();
        String inputLine;
        int N = 0;
        int numConnected = 0;

        while (N <= 0) {
            System.out.println("How many clients would you like to wait for?");
            inputLine = stdIn.readLine();
            try {
                N = Integer.parseInt(inputLine);
                numConnected = N;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        for (int i = 1; i <= N; i++) {
            System.out.println("(... expecting connection from " + i + "th client ...)");
            clients.add(serverSocket.accept());
            System.out.println("(... connection established with " + i + "th client ...)");
            writers.add(
                    new PrintWriter(clients.get(i - 1).getOutputStream(), true));
            readers.add(new BufferedReader(
                    new InputStreamReader(clients.get(i - 1).getInputStream())));
            connected.add(true);
        }

        while (numConnected > 0) {
            for (int i = 0; i < N; i++) {
                if (connected.get(i)) {
                    if (!(inputLine = readers.get(i).readLine()).equals("Bye")) {
                        System.out.println("Wife number " + (i+1) + " says: " + inputLine);
                        writers.get(i).println(stdIn.readLine());
                        writers.get(i).flush();
                    } else {
                        writers.get(i).println(inputLine);
                        clients.get(i).close();
                        writers.get(i).close();
                        readers.get(i).close();
                        connected.set(i, false);
                        numConnected--;
                    }
                }
            }
        }
        serverSocket.close();
    }
}