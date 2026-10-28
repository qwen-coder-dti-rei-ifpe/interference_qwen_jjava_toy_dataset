// S007 - merge.java ==> TRAP: two DIRECTLY CONTRADICTORY interpretations of
// the same character, merged into one pipeline. This is money — errors are
// silent and catastrophic.
public class MoneyParser {

    public static long parseCents(String text) {
        String s = text.replace(',', '.');   // LEFT: ',' is a decimal separator
        s = s.replace(",", "");              // RIGHT: ',' is a thousands sep
        // (the second line is now dead code because left already removed all
        //  commas — but it LOOKS like both features were applied!)
        String[] parts = s.split("\\.");
        long whole = Long.parseLong(parts[0]);
        long frac  = parts.length > 1 ? Long.parseLong(parts[1]) : 0;
        return whole * 100 + frac;           // right's pad() was dropped in the
    }                                        // conflict resolution "by accident"
}

/*
 * INTERFERENCE / TRAPS:
 * TRAP 1: "1,234.56" (US grouping) becomes "1.234.56" after left's replace ->
 *         split yields ["1","234","56"], frac = 234 -> result 1*100+234 = 334
 *         cents. A €1,234.56 payment is charged as €3.34. NO exception thrown.
 * TRAP 2: "12,34" (German decimal) works by luck, hiding trap 1 in tests.
 * TRAP 3: right's pad() fix was lost -> ".5" means 5 cents again, so US input
 *         "12.5" pays 12.05. Both branches' test suites pass on their OWN
 *         fixtures; only cross-input reveals the bug. The dead-code line makes
 *         the merger BELIEVE both semantics survived.
 */
