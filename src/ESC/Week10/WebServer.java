package ESC.Week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(4321, 1000);

        long startTime = 0;
        while (true) {
            Socket connection = socket.accept();
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) throws Exception {
        //todo
    }

    private static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");
        BigInteger zero = new BigInteger("0");

        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }

            i = i.add(new BigInteger("1"));
        }

        assert (false);
        return null;
    }
}
