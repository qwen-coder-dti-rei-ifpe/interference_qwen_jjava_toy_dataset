// S006 - base.java
// Base version: config loader. Keys are case-INSENSITIVE by convention?
// No: the base code is case-SENSITIVE and documented so in callers.
import java.util.HashMap;
import java.util.Map;

public class Config {

    private final Map<String, String> data = new HashMap<>();

    public void load(String[][] pairs) {
        for (String[] kv : pairs) {
            data.put(kv[0], kv[1]);           // exact key, case preserved
        }
    }

    public String get(String key) {
        return data.get(key);                 // case-sensitive lookup
    }
}
