package ESC.Week12;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by HanWei on 13/4/2017.
 */
public class NonblockingCounterFixed {
    private AtomicStampedReference<AtomicInteger> value = new AtomicStampedReference<>(new AtomicInteger(0), 0);

    public int getValue() {
        return value.getStamp();
    }

    public int increment() {
        AtomicInteger oldref;
        int oldstamp;
        AtomicInteger newref;
        do {
            oldref = value.getReference();
            oldstamp = value.getStamp();
            newref = new AtomicInteger(oldref.get());
            newref.incrementAndGet();
        } while (!value.compareAndSet(oldref, newref, oldstamp, oldstamp + 1));
        return oldref.get() + 1;
    }
}
