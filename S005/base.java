// S005 - base.java
// Base version: a counter used by the metrics pipeline. NOT thread-safe,
// but single-threaded by design; callers externally synchronise.
public class Counter {

    private int value = 0;

    public void increment() {
        value = value + 1;
    }

    public int get() {
        return value;
    }
}
