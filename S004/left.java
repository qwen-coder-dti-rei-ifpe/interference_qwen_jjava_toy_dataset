// S004 - left.java (Developer A)
// Intent: performance — replace manual loop with String.join, and make
// first() throw instead of returning null ("fail fast").
import java.util.List;

public class ListUtils {

    public static String first(List<String> items) {
        if (items.isEmpty()) {
            throw new java.util.NoSuchElementException("empty list");
        }
        return items.get(0);
    }

    public static String join(List<String> items) {
        return String.join(" ", items);   // same behaviour, faster
    }
}
