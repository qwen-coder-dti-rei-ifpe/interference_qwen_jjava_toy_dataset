// S004 - right.java (Developer B)
// Intent: reporting feature — join now uses ", " separator and adds a
// trailing period; first() gets a default-value overload.
import java.util.List;

public class ListUtils {

    public static String first(List<String> items) {
        return items.isEmpty() ? null : items.get(0);   // unchanged
    }

    public static String first(List<String> items, String def) {  // new overload
        return items.isEmpty() ? def : items.get(0);
    }

    /** Now formatted for human reports: comma-separated + final dot. */
    public static String join(List<String> items) {
        if (items.isEmpty()) return "";
        StringBuilder sb = new StringBuilder(String.join(", ", items));
        sb.append('.');
        return sb.toString();
    }
}
