// S008 - base.java
// Base version: DTO with a derived getter. The JSON serialiser calls BOTH
// the field getter and every getX() method, so `total` must stay consistent.
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Integer> lineCents = new ArrayList<>();

    public void addLine(int cents) {
        lineCents.add(cents);
    }

    public List<Integer> getLineCents() {
        return lineCents;                 // live list on purpose (serialiser)
    }

    public int getTotal() {
        int sum = 0;
        for (int c : lineCents) sum += c;
        return sum;
    }
}
