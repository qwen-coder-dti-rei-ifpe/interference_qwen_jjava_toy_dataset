// S001 - base.java
// Base version: a discount calculator with a single, well-known rule.
public class DiscountCalculator {

    // Loyalty tier used by the pricing engine.
    public static final int GOLD_TIER = 2;

    /**
     * Applies the loyalty discount to an order total.
     * Contract: returned value is ALWAYS in cents and never negative.
     */
    public long applyDiscount(long totalCents, int tier) {
        if (totalCents < 0) {
            return 0;
        }
        if (tier == GOLD_TIER) {
            // 10% off for gold members
            return totalCents - (totalCents / 10);
        }
        return totalCents;
    }
}
