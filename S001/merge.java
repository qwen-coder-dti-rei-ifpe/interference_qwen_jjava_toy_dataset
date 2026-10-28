// S001 - merge.java  ==> TRAP: "textually clean, semantically broken" merge.
//
// A naive git merge of left+right resolves the overlapping region by taking
// one side (here: LEFT wins on the tier constants and the body), while
// silently keeping a RIGHT-only line (PLATINUM_TIER / the -1 sentinel).
// The result COMPILES and passes a smoke test, but is wrong in 3 ways:
//   TRAP 1: GOLD_TIER renumbered to 3, yet other code (and PLATINUM_TIER=4)
//           was written against the old numbering -> tier 2 now means SILVER.
//   TRAP 2: applyDiscount() now returns EUROS (rounded) although its name,
//           parameter and every caller still speak CENTS.
//   TRAP 3: negative totals return 0 (left) instead of the -1 fraud sentinel
//           (right); downstream `if (r == -1)` checks never fire.
public class DiscountCalculator {

    public static final int BRONZE_TIER = 1;
    public static final int SILVER_TIER = 2;   // was GOLD_TIER in base!
    public static final int GOLD_TIER = 3;     // left's re-numbering wins
    public static final int PLATINUM_TIER = 4; // right's addition survives

    public long applyDiscount(long totalCents, int tier) {
        if (totalCents < 0) {
            return 0;                       // left's behaviour: fraud signal LOST
        }
        double discounted;
        if (tier == GOLD_TIER) {
            discounted = totalCents * 0.90;
        } else if (tier == SILVER_TIER) {
            discounted = totalCents * 0.95;
        } else if (tier == PLATINUM_TIER) {
            discounted = totalCents * 0.75; // merged-in from right, looks fine
        } else {
            discounted = totalCents;
        }
        return Math.round(discounted / 100.0); // <-- UNIT BUG introduced by left
    }
}

/*
 * WHY THIS CONFUSES THE PERSON RESPONSIBLE FOR THE MERGE:
 * - No conflict markers at all in several hunks -> "merge looks easy".
 * - Both sides kept the SAME method signature, so nothing fails to compile.
 * - Only reading base+left+right together reveals: unit change (cents->euros),
 *   constant re-numbering, and a dropped security sentinel.
 */
