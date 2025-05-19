package utils;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private static final Map<String, Object> contextData = new HashMap<>();

    public static void set(String key, Object value) {
        contextData.put(key, value);
    }

    public static Object get(String key) {
        return contextData.get(key);
    }

    public static String getString(String key) {
        Object value = contextData.get(key);
        return value != null ? value.toString() : null;
    }

    public static void clear() {
        contextData.clear();
    }
}
