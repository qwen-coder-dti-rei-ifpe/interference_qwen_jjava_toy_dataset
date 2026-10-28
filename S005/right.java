// S005 - right.java (Developer B)
// Intent: lock-free performance with AtomicInteger + new batch API.
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private final AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        value.incrementAndGet();
    }

    // New feature: add n at once.
    public void add(int n) {
        value.addAndGet(n);
    }

    public int get() {
        return value.get();
    }
}
