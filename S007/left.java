// S007 - left.java (Developer A)
// Intent: i18n support — German/French locales use ',' as decimal separator.
// Now the parser ACCEPTS BOTH separators (normalises ',' to '.').
public class MoneyParser {

    public static long parseCents(String text) {
        String normalised = text.replace(',', '.');   // accept "12,34" too
        String[] parts = normalised.split("\\.");
        long whole = Long.parseLong(parts[0]);
        long frac  = parts.length > 1 ? Long.parseLong(parts[1]) : 0;
        return whole * 100 + frac;
    }
}
