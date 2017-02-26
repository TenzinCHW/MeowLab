package ESC.Week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by HanWei on 25/2/2017.
 */
public class FactorPrimeClient {
    public static void main(String[] args) throws Exception {
//        String hostName = "localhost";
        //String hostIP = "10.11.3.28";
        //String hostName = "fe80::7517:c1af:b2bb:da73%4";
        int portNumber = 4321;

//        Socket echoSocket = new Socket(hostName, portNumber);
        Socket echoSocket = new Socket();
        SocketAddress sockaddr = new InetSocketAddress("localhost", portNumber);
        echoSocket.connect(sockaddr, 100);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(echoSocket.getInputStream()));
        String userInput;

//        do {
//            userInput = in.readLine().trim();
//        } while (userInput == null);
        BigInteger number = null;
        BigInteger interval = null;
        int worker = -1;

        while ((userInput = in.readLine()) != null) {
            number = new BigInteger(userInput);
            userInput = in.readLine();
            interval = new BigInteger(userInput);
            userInput = in.readLine();
            worker = Integer.parseInt(userInput);
            break;
        }

        long start = interval.multiply(BigInteger.valueOf(worker)).longValue();
        long end = interval.multiply(BigInteger.valueOf(worker + 1)).longValue();

        String answer = isSemiPrime(number, start, end);
        out.println(answer);

        echoSocket.close();
        in.close();
        out.close();
    }

    public static String isSemiPrime(BigInteger number, long start, long end) {
        BigInteger den;
        BigInteger mod;
        if (start == 0) start = 2;
        if (number.mod(BigInteger.valueOf(start)).equals(BigInteger.ZERO))
            return number.divide(BigInteger.valueOf(2)).toString() + " " + 2;
        if (start % 2 == 0) start++;
        for (long i = start; i < end; i += 2) {
            den = BigInteger.valueOf(i);
            mod = number.mod(den);
            if (mod.equals(BigInteger.ZERO)) {
                BigInteger num = number.divide(den);
                return  num.toString() + " " + den.toString();
            }
        }
        return "No primes.";
    }
}
