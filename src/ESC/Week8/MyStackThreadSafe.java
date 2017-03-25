package ESC.Week8;

public class MyStackThreadSafe {
    private final int maxSize;
    private long[] stackArray; //guarded by "this"
    private int top; //invariant: top < stackArray.length && top >= -1; guarded by "this"

    //pre-condition: s > 0
    //post-condition: maxSize == s && top == -1 && stackArray != null
    public MyStackThreadSafe(int s) { //Do we need "synchronized" for the constructor?
        assert s > 0;
        maxSize = s;
        stackArray = new long[maxSize];
        top = -1;
    }

    //pre-condition: top >= 0
    //post-condition: the top element is removed
    public synchronized long pop() {
        assert top >= 0;
        long toReturn;

        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        toReturn = stackArray[top--];
        notifyAll();
        return toReturn;
    }

    //pre-condition: true
    //post-condition: the elements are un-changed. the return value is true iff the stack is empty.
    public synchronized boolean isEmpty() {
        return (top == -1);
    }
}