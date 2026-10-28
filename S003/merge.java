// S003 - merge.java ==> TRAP: overload-based "compatibility" that silently
// changes which method callers bind to.
//
// The merger wanted both features (Optional API + cache) and "kept everyone
// happy" by retaining BOTH return types as overloads... but Java does NOT
// allow overloading on return type alone, so the merger renamed one — and now
// old call sites of findName(long) resolve to the SENTINEL version while new
// code uses the Optional version. Two different "not found" languages coexist.
import java.util.Optional;

public class UserRepository {

    public static final String UNKNOWN = "<unknown>"; // from right

    private long lastId = -1;
    private String lastName = null;

    // LEFT's modernised API kept...
    public Optional<String> findNameOpt(long id) {     // renamed! callers updated?
        if (id == 1L) return Optional.of("alice");
        if (id == 2L) return Optional.of("bob");
        return Optional.empty();
    }

    // ...and RIGHT's classic-signature version also kept.
    public String findName(long id) {                  // base signature survives
        if (id == lastId) {
            return lastName;
        }
        String name;
        if (id == 1L)      name = "alice";
        else if (id == 2L) name = "bob";
        else               name = UNKNOWN;             // sentinel, not null
        lastId = id;
        lastName = name;
        return name;
    }

    public String findNameOrDefault(long id, String def) {
        return findNameOpt(id).orElse(def);
    }
}

/*
 * INTERFERENCE / TRAPS:
 * TRAP 1: `if (repo.findName(id) == null)` compiles forever but is now dead
 *         code -> unknown users render as "<unknown>" in emails/UIs.
 * TRAP 2: two entry points (findName / findNameOpt) can DIVERGE when someone
 *         later adds user 3 to only one of them.
 * TRAP 3: the cache fields make the object non-thread-safe; left's stateless
 *         design hid this risk, and the merged file inherits it silently.
 */
