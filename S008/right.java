// S008 - right.java (Developer B)
// Intent: performance — cache the total incrementally instead of summing on
// every call. addLine() must keep the cache in sync.
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Integer> lineCents = new ArrayList<>();
    private int cachedTotal = 0;

    public void addLine(int cents) {
        lineCents.add(cents);
        cachedTotal += cents;          // invariant: cachedTotal == sum(lines)
    }

    public List<Integer> getLineCents() {
        return lineCents;              // still live list (unchanged from base)
    }

    public int getTotal() {
        return cachedTotal;
    }
}
