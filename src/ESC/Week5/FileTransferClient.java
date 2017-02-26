package ESC.Week5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by HanWei on 26/2/2017.
 */
public class FileTransferClient {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        int portNumber = 4321;
//        BufferedReader stdIn =
//                new BufferedReader(
//                        new InputStreamReader(System.in));

        Socket echoSocket = new Socket();
        SocketAddress sockaddr = new InetSocketAddress("localhost", portNumber);
        echoSocket.connect(sockaddr, 100);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(echoSocket.getInputStream()));
        String input;
        String ack;
        String fileName = "meow.txt";   // Change me
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        while ((input = reader.readLine()) != null) {
            if (input != null) {
                out.println(input);
                while (!(ack = in.readLine()).equals("Acknowledged")); {
                    out.println(input);
                }
            }
        }
        out.println("ThisIsDefinitelyTheEnd");

        echoSocket.close();
        in.close();
        out.close();
    }
}
