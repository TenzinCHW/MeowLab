package ESC.Week9;

//this class must be thread-safe. why?
public class MyCyclicBarrierAnswer {
    private int count = 0;
    private Runnable torun;

    public MyCyclicBarrierAnswer(int count, Runnable torun) {
        this.count = count;
        this.torun = torun;
    }

    public MyCyclicBarrierAnswer(int count) {
        this.count = count;
    }

    //complete the implementation below.
    //hint: use wait(), notifyAll()
    public void await() {
        try {
            count--;
            if (count != 0) wait();
            else {
                if (torun != null) torun.run();
                notifyAll();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
