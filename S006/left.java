// S006 - left.java (Developer A)
// Intent: UX fix — users keep typing "Timeout" vs "timeout". Normalise keys
// to lower case on BOTH write and read paths.
import java.util.HashMap;
import java.util.Map;

public class Config {

    private final Map<String, String> data = new HashMap<>();

    public void load(String[][] pairs) {
        for (String[] kv : pairs) {
            data.put(kv[0].toLowerCase(), kv[1]);
        }
    }

    public String get(String key) {
        return data.get(key.toLowerCase());
    }
}
