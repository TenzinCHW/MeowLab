package ESC.Week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by HanWei on 26/2/2017.
 */
public class ChatRoomClient2 {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        int portNumber = 4321;
        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        System.out.println("What's your name?");
        String userInput;
        userInput = stdIn.readLine();
        Socket echoSocket = new Socket();
        SocketAddress sockaddr = new InetSocketAddress("localhost", portNumber);
        echoSocket.connect(sockaddr, 100);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(echoSocket.getInputStream()));
        out.println(userInput);

        do {
            userInput = stdIn.readLine();
            out.println(userInput);
            out.flush();
        } while (!userInput.equals("Bye"));

        echoSocket.close();
        in.close();
        out.close();
        stdIn.close();
    }
}
