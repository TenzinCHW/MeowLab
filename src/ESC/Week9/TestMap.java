package ESC.Week9;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HanWei on 25/3/2017.
 * <p>
 * For testing WorkerThread
 */

public class TestMap {
    public static void main(String[] args) {
        int size = 5;
        int numTrials = 10;
        ConcurrentHashMap<String, Integer> conc = new ConcurrentHashMap();
        Map<String, Integer> sync = Collections.synchronizedMap(new HashMap<String, Integer>());

        WorkerThread[] concTest = new WorkerThread[size];
        WorkerThread[] syncTest = new WorkerThread[size];

        long concTotal = 0;
        long syncTotal = 0;

        for (int i = 0; i < numTrials; i++) {
            concTotal += testMap(concTest, conc, size);
            syncTotal += testMap(syncTest, sync, size);
        }

        concTotal /= numTrials;
        syncTotal /= numTrials;

        System.out.println("Average time taken for Concurrent HashMap: " + concTotal);
        System.out.println("Average time taken for Synchronized Map: " + syncTotal);
    }

    private static void setWork(WorkerThread[] list, int size, Map<String, Integer> map) {
        for (int i = 0; i < size; i++) {
            list[i] = new WorkerThread(map);
        }
    }

    private static long testMap(WorkerThread[] list, Map<String, Integer> map, int size) {
        map.clear();
        setWork(list, size, map);
        long start = System.currentTimeMillis();
        for (int j = 0; j < size; j++) {
            list[j].start();
            try {
                list[j].join();
            } catch (InterruptedException e) {
                System.out.println("Someone couldn't finish.");
            }
        }
        return System.currentTimeMillis() - start;
    }
}
