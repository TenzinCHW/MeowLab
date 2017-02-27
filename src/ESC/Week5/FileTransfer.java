package ESC.Week5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by HanWei on 26/2/2017.
 */
public class FileTransfer {
    private ServerSocket serverSocket;
    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;
    private int fileNum = 0;

    public static void main(String[] args) {
        FileTransfer fileTransferrer = new FileTransfer();
        fileTransferrer.startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(4321);
        } catch (Exception e) {
            System.out.println("Could not start server. Port is being used (most likely).");
            return;
        }

        while (true) {
            acceptNewClient();
            receiveData();
        }
    }

    private void acceptNewClient() {
        try {
            client = serverSocket.accept();
            writer = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            return;
        }
    }

    private void receiveData() {
        String input;
        try {
            PrintWriter writeToFile = new PrintWriter("File" + fileNum + ".txt", "UTF-8");
            do {
                input = reader.readLine();
                if (!input.equals("ThisIsDefinitelyTheEnd") && !input.equals("null")) {
                    writer.println("Acknowledged");
                    writeToFile.println(input);
                    writeToFile.flush();
                } else break;
            } while ((input = reader.readLine()) != null);
            writeToFile.close();
            fileNum++;
        } catch (IOException e) {
            System.out.println("The client disconnected (I think).");
        }
    }
}
