package ESC.Week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

/**
 * Created by HanWei on 28/3/2017.
 */
public class MultipleClientStop {
    public static void main(String[] args) throws Exception {
        int numberOfClients = 10; //vary this number here
        long startTime = System.currentTimeMillis();
        BigInteger n = new BigInteger("4294967297");
        //BigInteger n = new BigInteger("239839672845043");
        Thread[] clients = new Thread[numberOfClients];

        for (int i = 0; i < numberOfClients; i++) {
            if (i == numberOfClients - 4) clients[i] = new Thread(new ClientStop());
            else clients[i] = new Thread(new ESC.Week10.Client(n));
            clients[i].start();
        }

        for (int i = 0; i < numberOfClients; i++) {
            clients[i].join();
        }
        System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
    }
}

class ClientStop implements Runnable {

    public void run() {
        String hostName = "localhost";
        int portNumber = 4321;

        try {
            //long startTime = System.currentTimeMillis();
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            out.println("stop");
            out.flush();
            in.readLine();
            //System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Server got problem");
        }
    }
}

