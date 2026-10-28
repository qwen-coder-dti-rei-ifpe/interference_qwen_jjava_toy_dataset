// S004 - base.java
// Base version: list utilities used by many modules.
import java.util.List;

public class ListUtils {

    /** Returns the first element or null for an empty list. */
    public static String first(List<String> items) {
        return items.isEmpty() ? null : items.get(0);
    }

    /** Joins with a single space. */
    public static String join(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(items.get(i));
        }
        return sb.toString();
    }
}
