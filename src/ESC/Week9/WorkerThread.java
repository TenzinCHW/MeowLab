package ESC.Week9;

import java.util.Map;

public class WorkerThread extends Thread {
    private Map<String, Integer> map = null;

    public WorkerThread(Map<String, Integer> map) {
        this.map = map;
    }

    public void run() {
        for (int i = 0; i < 500000; i++) {
            // Return 2 random integers
            Integer newInteger1 = (int) Math.ceil(Math.random());
            Integer newInteger2 = (int) Math.ceil(Math.random());
            // 1. Attempt to retrieve a random Integer element
            map.get(String.valueOf(newInteger1));
            // 2. Attempt to insert a random Integer element
            map.put(String.valueOf(newInteger2), newInteger2);
        }
    }
}