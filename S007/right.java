// S007 - right.java (Developer B)
// Intent: support thousands separators used by US imports, e.g. "1,234.56".
// Strategy: STRIP all ',' as grouping characters, keep '.' decimal only.
public class MoneyParser {

    public static long parseCents(String text) {
        String stripped = text.replace(",", "");      // "1,234.56" -> "1234.56"
        String[] parts = stripped.split("\\.");
        long whole = Long.parseLong(parts[0]);
        long frac  = parts.length > 1 ? Long.parseLong(pad(parts[1])) : 0;
        return whole * 100 + frac;
    }

    private static String pad(String frac) {          // ".5" -> 50 cents
        return frac.length() == 1 ? frac + "0" : frac;
    }
}
