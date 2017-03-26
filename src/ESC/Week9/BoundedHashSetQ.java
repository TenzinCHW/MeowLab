package ESC.Week9;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSetQ<T> {
    private final Set<T> set;
    private Semaphore semaphore;

    public BoundedHashSetQ(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.semaphore = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        semaphore.acquire();
        boolean setted = false;
        try {
            setted = set.add(o);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (!setted) semaphore.release();
            return setted;
        }
    }

    public boolean remove(Object o) {
        boolean removed = set.remove(o);
        if (removed) semaphore.release();
        return removed;
    }
}