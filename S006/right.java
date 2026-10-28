// S006 - right.java (Developer B)
// Intent: add typed accessors + defaults. Keys must stay EXACT because the
// XML schema distinguishes <Port> from <PORT> for legacy systems.
import java.util.HashMap;
import java.util.Map;

public class Config {

    private final Map<String, String> data = new HashMap<>();

    public void load(String[][] pairs) {
        for (String[] kv : pairs) {
            data.put(kv[0], kv[1]);
        }
    }

    public String get(String key) {
        return data.getOrDefault(key, "");     // never null (new contract)
    }

    // New feature used by the deployment scripts:
    public int getInt(String key, int fallback) {
        String v = data.get(key);
        return v == null ? fallback : Integer.parseInt(v.trim());
    }
}
