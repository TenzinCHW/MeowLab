package ESC.Week9;

//this class must be thread-safe. why?
public class MyCyclicBarrier {
    private int count = 0;
    private Runnable torun;

    public MyCyclicBarrier(int count, Runnable torun) {
        this.count = count;
        this.torun = torun;
    }

    public MyCyclicBarrier(int count) {
        this.count = count;
    }

    //complete the implementation below.
    //hint: use wait(), notifyAll()
    public void await() {

    }
}
