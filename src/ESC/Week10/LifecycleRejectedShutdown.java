package ESC.Week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by HanWei on 31/3/2017.
 */
public class LifecycleRejectedShutdown {
    private static final int NTHREADS = 85;
    private static final ExecutorService exec = new ScheduledThreadPoolExecutor(NTHREADS);

    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(4321, 1000);

        exec.shutdown();    // Will throw RejectedExecutionException after shutting down
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }

    protected static void handleRequest(Socket connection) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);
            BigInteger read = factor(new BigInteger(reader.readLine()));
            writer.write(read.toString());
            reader.close();
            writer.close();
            connection.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
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
