// S006 - merge.java ==> TRAP: asymmetric normalisation. The merge kept
// LEFT's lower-casing on ONE path and RIGHT's exact-key logic on the other.
import java.util.HashMap;
import java.util.Map;

public class Config {

    private final Map<String, String> data = new HashMap<>();

    public void load(String[][] pairs) {
        for (String[] kv : pairs) {
            data.put(kv[0].toLowerCase(), kv[1]);   // LEFT applied here
        }
    }

    public String get(String key) {
        return data.getOrDefault(key.toLowerCase(), ""); // LEFT + right default
    }

    public int getInt(String key, int fallback) {
        String v = data.get(key);                    // RIGHT forgot toLowerCase!
        return v == null ? fallback : Integer.parseInt(v.trim());
    }
}

/*
 * INTERFERENCE / TRAPS:
 * TRAP 1: keys are STORED lowercase but getInt() looks them up with the raw
 *         key -> getInt("Port", 8080) ALWAYS returns the fallback. Silent
 *         misconfiguration: services boot on default ports in production.
 * TRAP 2: two different "missing key" languages coexist: get() returns ""
 *         (right's never-null contract) while callers from base still do
 *         `if (cfg.get(k) == null)` -> dead branch, config typos unnoticed.
 * TRAP 3: left's normalisation broke right's documented legacy rule that
 *         <Port> and <PORT> are DIFFERENT keys — a collision now silently
 *         overwrites one value with the other during load().
 */
