// S005 - left.java (Developer A)
// Intent: make it thread-safe with plain synchronized methods.
public class Counter {

    private int value = 0;

    public synchronized void increment() {
        value = value + 1;
    }

    public synchronized int get() {
        return value;
    }
}
