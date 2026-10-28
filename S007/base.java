// S007 - base.java
// Base version: payment amount parser shared by checkout and invoicing.
public class MoneyParser {

    /** Parses "12.34" -> 1234 cents. Always uses '.' as decimal separator. */
    public static long parseCents(String text) {
        String[] parts = text.split("\\.");
        long whole = Long.parseLong(parts[0]);
        long frac  = parts.length > 1 ? Long.parseLong(parts[1]) : 0;
        return whole * 100 + frac;
    }
}
