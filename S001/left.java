// S001 - left.java (Developer A)
// Intent: support more tiers + fix rounding for very small carts.
// SIDE EFFECT (hidden): the unit of the result changed from CENTS to EUROS.
public class DiscountCalculator {

    // Tiers were re-numbered: GOLD moved from 2 to 3.
    public static final int BRONZE_TIER = 1;
    public static final int SILVER_TIER = 2;
    public static final int GOLD_TIER = 3;

    public long applyDiscount(long totalCents, int tier) {
        if (totalCents < 0) {
            return 0;
        }
        double discounted;
        if (tier == GOLD_TIER) {
            discounted = totalCents * 0.90;
        } else if (tier == SILVER_TIER) {
            discounted = totalCents * 0.95;
        } else {
            discounted = totalCents;
        }
        // "Fix" for tiny carts: convert to euros and round to 2 decimals...
        // ...but the method still returns a long, so this truncates to whole euros!
        return Math.round(discounted / 100.0);
    }
}
