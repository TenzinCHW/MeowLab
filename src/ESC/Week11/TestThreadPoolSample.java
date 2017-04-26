package ESC.Week11;

import java.util.concurrent.*;

import junit.framework.TestCase;

public class TestThreadPoolSample extends TestCase {

    public void testPoolExpansion() throws InterruptedException {
        int max_pool_size = 10;
        ExecutorService exec = Executors.newFixedThreadPool(max_pool_size);

        Runnable runTest[] = new Runnable[100];

        for (int i = 0; i < 100; i++) {
            runTest[i] = new Runnable() {
                @Override
                public void run() {
                    testThread();
                }
            };
        }

        for (int i = 0; i < 100; i++) {
            exec.execute(runTest[i]);
        }

        //hint: you can use the following code to get the number of active threads in a thread pool
        int numThreads = 0;
        if (exec instanceof ThreadPoolExecutor) {
        	numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
        }
        assertTrue(numThreads <= max_pool_size);
        exec.shutdownNow();
    }

    public static void testThread() {
        int meow = 100;
        int woof = 304;
        int ruff = meow * woof;
    }
}
