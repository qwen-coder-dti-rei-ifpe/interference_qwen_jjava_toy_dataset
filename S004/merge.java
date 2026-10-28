// S004 - merge.java ==> TRAP: "take the union of both sides" merge.
//
// join() conflicted textually (both rewrote it). The merger kept LEFT's
// one-liner because it "looks equivalent to base", then added RIGHT's
// overload below — producing a file where each individual line is justified
// by SOME branch, yet the combination matches NEITHER branch's intent.
import java.util.List;

public class ListUtils {

    public static String first(List<String> items) {
        if (items.isEmpty()) {
            throw new java.util.NoSuchElementException("empty list"); // left
        }
        return items.get(0);
    }

    public static String first(List<String> items, String def) {      // right
        return items.isEmpty() ? def : items.get(0);                  // right
    }

    public static String join(List<String> items) {
        return String.join(" ", items);                                // left
    }
}

/*
 * INTERFERENCE / TRAPS:
 * TRAP 1: first(items) throws while first(items, def) returns null-safe
 *         default -> call sites that pass a variable `def` that happens to be
 *         null behave totally differently from the 1-arg version.
 * TRAP 2: RIGHT's report format (", " + '.') was silently dropped: CSV-ish
 *         exports and PDF reports now emit space-separated, dot-less strings.
 *         No test fails unless someone tests the REPORT path.
 * TRAP 3: LEFT made first() throw; RIGHT assumed it still can return null
 *         (`first(list)` result used in ternaries elsewhere) -> runtime
 *         NoSuchElementException in production for empty lists, which the
 *         merged code "protects" via the def-overload only on some paths.
 */
