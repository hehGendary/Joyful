package MainFiles.AST.Library.Variables;

import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public final class Variables {

    public static Value asValue(double DOU) {
        return new NumberValue(DOU);
    }

    private static Map<String, Value> variables;
    private static final Stack<Map<String, Value>> stack;


    static {
        stack = new Stack<>();
        variables = new HashMap<>();
        variables.put("pi", asValue(Math.PI));
        variables.put("e", asValue(Math.E));
        variables.put("phi", asValue(1.618));
        variables.put("true", asValue(1));
        variables.put("false", asValue(0));
    }

    public static void push() {
        stack.push(new HashMap<>(variables));
    }

    public static void pop() {
        variables = stack.pop();
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