package ESC.Week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by HanWei on 26/2/2017.
 */
public class FactorPrimeServerMult {
    private ServerSocket serverSocket;
    private ArrayList<Socket> clients;
    private ArrayList<PrintWriter> writers;
    private ArrayList<BufferedReader> readers;
    private BufferedReader stdIn;
    private int N;

    public static void main(String[] args) {
        FactorPrimeServerMult server = new FactorPrimeServerMult();
        server.startServer();
        BigInteger numToFind = new BigInteger("4294967297");
//        BigInteger nextNumToFind = new BigInteger("1127451830576035879");
//        BigInteger finalNumToFind = new BigInteger("160731047637009729259688920385507056726966793490579598495689711866432421212774967029895340327197901756096014299132623454583177072050452755510701340673282385647899694083881316194642417451570483466327782135730575564856185546487053034404560063433614723836456790266457438831626375556854133866958349817172727462462516466898479574402841071703909138062456567624565784254101568378407242273207660892036869708190688033351601539401621576507964841597205952722487750670904522932328731530640706457382162644738538813247139315456213401586618820517823576427094125197001270350087878270889717445401145792231674098948416888868250143592026973853973785120217077951766546939577520897245392186547279572494177680291506578508962707934879124914880885500726439625033021936728949277390185399024276547035995915648938170415663757378637207011391538009596833354107737156273037494727858302028663366296943925008647348769272035532265048049709827275179381252898675965528510619258376779171030556482884535728812916216625430187039533668677528079544176897647303445153643525354817413650848544778690688201005274443717680593899");
        BigInteger answer[] = server.findSemiPrimeFactor(numToFind);
        System.out.println("First factor is: ");
        System.out.println(answer[0]);
        System.out.println("Second factor is: ");
        System.out.println(answer[1]);
        server.closeServer();
    }

    public FactorPrimeServerMult() {
        clients = new ArrayList<>();
        writers = new ArrayList<>();
        readers = new ArrayList<>();
        stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        N = 0;
    }

    public void startServer() {
        String inputLine;
        try {
            serverSocket = new ServerSocket(4321);
            serverSocket.setSoTimeout(5000);
        } catch (Exception e) {
            System.out.println("Could not start server. Port is being used (most likely).");
            return;
        }
        try {
            int i = 1;
            while (true) {
                System.out.println("(... expecting connection from " + i + "th client ...)");
                clients.add(serverSocket.accept());
                System.out.println("(... connection established with " + i + "th client ...)");
                writers.add(
                        new PrintWriter(clients.get(i - 1).getOutputStream(), true));
                readers.add(new BufferedReader(
                        new InputStreamReader(clients.get(i - 1).getInputStream())));
                i++;
            }
        } catch (Exception e) {
            if (clients.size() == 0) {
                System.out.println("No one wants to work for me :(");
                System.exit(-1);
            } else {
                System.out.println("No more clients accepted.");
                N = clients.size();
            }
        }
    }

    public BigInteger[] findSemiPrimeFactor(BigInteger number) {
        splitSemiPrime(number, N);
        BigInteger result[] = new BigInteger[2];
        BigInteger firstFactor = null;
        BigInteger secondFactor;
        String line;
        while (firstFactor == null) {
            for (BufferedReader reader :
                    readers) {
                try {
                    if ((line = reader.readLine()) != null) {
                        if (line.equals("No primes.")) continue;
                        String stResult[] = line.split(" ");
                        firstFactor = new BigInteger(stResult[0]);
                        secondFactor = new BigInteger(stResult[1]);
                        result[0] = firstFactor;
                        result[1] = secondFactor;
                        return result;
                    }
                } catch (IOException e) {
                    firstFactor = null;
                    continue;
                }
            }
        }
        return result;
    }

    private void splitSemiPrime(BigInteger number, int numWorkers) {
        BigInteger interval = sqrtBigInt(number).divide(BigInteger.valueOf(numWorkers));

        for (int i = 0; i < numWorkers; i++) {
            sendSemiPrime(number, interval, i);
        }
    }

    private void sendSemiPrime(BigInteger number, BigInteger intervalLength, int worker) {
        PrintWriter send = writers.get(worker);
        send.println(number.toString());
        send.println(intervalLength.toString());
        send.println(worker);
    }

    public void closeServer() {
        for (int i = 0; i < N; i++) {
            try {
                writers.get(i).println();
                clients.get(i).close();
                writers.get(i).close();
                readers.get(i).close();
            } catch (IOException e) {
                System.out.println("Couldn't print the " + i + "th client.");
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Couldn't close the server.");
        }
    }

    private BigInteger sqrtBigInt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }
}
