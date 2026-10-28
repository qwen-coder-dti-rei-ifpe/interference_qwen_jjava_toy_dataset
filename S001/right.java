// S001 - right.java (Developer B)
// Intent: add a fraud guard for negative totals and a PLATINUM tier.
// SIDE EFFECT (hidden): relies on GOLD_TIER == 2 (the BASE numbering).
public class DiscountCalculator {

    public static final int GOLD_TIER = 2;
    public static final int PLATINUM_TIER = 4;

    public long applyDiscount(long totalCents, int tier) {
        if (totalCents < 0) {
            // Fraud guard: never silently return 0, flag with sentinel -1
            return -1;
        }
        if (tier == PLATINUM_TIER) {
            return totalCents - (totalCents / 4);   // 25% off
        }
        if (tier == GOLD_TIER) {
            // 10% off for gold members
            return totalCents - (totalCents / 10);
        }
        return totalCents;
    }
}
