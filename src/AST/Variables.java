package AST;

import java.util.HashMap;
import java.util.Map;

public final class Variables {

    public static Value asValue(double DOU) {
        return new NumberValue(DOU);
    }

    private static final Map<String, Value> variables;

    static {
        variables = new HashMap<>();
        variables.put("pi", asValue(Math.PI));
        variables.put("e", asValue(Math.E));
        variables.put("phi", asValue(1.618));
    }

    public static boolean isExists(String key) {
        return variables.containsKey(key);
    }

    public static Value get(String key) {
        if (!isExists(key)) return asValue(0);
        return variables.get(key);
    }

    public static void set(String key, Value value) {
        variables.put(key, value);
    }
}