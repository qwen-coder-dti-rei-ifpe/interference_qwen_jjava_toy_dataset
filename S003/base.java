// S003 - base.java
// Base version: repository lookup that returns null when not found.
public class UserRepository {

    /** Returns the user name, or null if unknown. Callers null-check. */
    public String findName(long id) {
        if (id == 1L) return "alice";
        if (id == 2L) return "bob";
        return null;
    }
}
