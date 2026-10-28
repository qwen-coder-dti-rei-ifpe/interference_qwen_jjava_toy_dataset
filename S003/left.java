// S003 - left.java (Developer A)
// Intent: modernise API to Optional. SIDE EFFECT: breaking signature change;
// every existing caller doing `if (findName(id) == null)` no longer compiles
// OR, worse, compiles against an overload kept "for compatibility".
import java.util.Optional;

public class UserRepository {

    public Optional<String> findName(long id) {
        if (id == 1L) return Optional.of("alice");
        if (id == 2L) return Optional.of("bob");
        return Optional.empty();
    }

    // Convenience added by left: never null, default instead of empty.
    public String findNameOrDefault(long id, String def) {
        return findName(id).orElse(def);
    }
}
