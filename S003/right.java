// S003 - right.java (Developer B)
// Intent: performance + caching. Keeps String return, but changes the
// "not found" language from null to the sentinel "<unknown>".
public class UserRepository {

    public static final String UNKNOWN = "<unknown>";

    private long lastId = -1;
    private String lastName = null;

    public String findName(long id) {
        if (id == lastId) {                 // cache hit
            return lastName;
        }
        String name;
        if (id == 1L)      name = "alice";
        else if (id == 2L) name = "bob";
        else               name = UNKNOWN;  // NOT null anymore!
        lastId = id;
        lastName = name;
        return name;
    }
}
