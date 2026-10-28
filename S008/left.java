// S008 - left.java (Developer A)
// Intent: encapsulation — never leak the internal list; return a copy.
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Integer> lineCents = new ArrayList<>();

    public void addLine(int cents) {
        lineCents.add(cents);
    }

    public List<Integer> getLineCents() {
        return new ArrayList<>(lineCents);   // defensive copy
    }

    public int getTotal() {
        int sum = 0;
        for (int c : lineCents) sum += c;
        return sum;
    }
}
