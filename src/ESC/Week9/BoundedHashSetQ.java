package ESC.Week9;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSetQ<T> {
    private final Set<T> set;

    public BoundedHashSetQ(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
    }

    public boolean add(T o) throws InterruptedException {
        return set.add(o);
    }

    public boolean remove(Object o) {
        return set.remove(o);
    }
}