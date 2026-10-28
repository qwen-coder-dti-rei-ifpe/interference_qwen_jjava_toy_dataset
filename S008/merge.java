// S008 - merge.java ==> TRAP: the two changes are individually fine but the
// combination breaks an invariant that NEITHER branch touched.
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Integer> lineCents = new ArrayList<>();
    private int cachedTotal = 0;                     // from right

    public void addLine(int cents) {
        lineCents.add(cents);
        cachedTotal += cents;                        // from right
    }

    public List<Integer> getLineCents() {
        return new ArrayList<>(lineCents);           // LEFT's defensive copy
    }

    public int getTotal() {
        return cachedTotal;                          // from right
    }
}

/*
 * INTERFERENCE / TRAPS:
 * In BASE, external code (the JSON serialiser / merge utilities) relied on
 * getLineCents() returning the LIVE list and did things like:
 *     cart.getLineCents().addAll(otherLines);   // used to update totals too,
 *                                              // because getTotal() summed live.
 * After the merge:
 * TRAP 1: addAll() mutates a THROWAWAY COPY -> lines silently vanish.
 * TRAP 2: even if they didn't, cachedTotal would never see them, so
 *         getTotal() disagrees with getLineCents() forever (right's invariant
 *         is only maintained through addLine(), left made bypass possible).
 * TRAP 3: no conflict markers existed between "return copy" (left) and
 *         "cached sum" (right) — git auto-merged them. The bug appears ONLY
 *         at runtime in the checkout flow, days after the merge.
 */
