package MainFiles.AST.Library.Classes;

import MainFiles.AST.JFExpection;

import java.util.HashMap;
import java.util.Map;

public final class Classes {

    private static final Map<String, ClassValue> classes;
    static {
        classes = new HashMap<>();
    }

    private Classes() { }

    public static void clear() {
        classes.clear();
    }

    public static Map<String, ClassValue> getFunctions() {
        return classes;
    }

    public static boolean isExists(String key) {
        return classes.containsKey(key);
    }

    public static ClassValue get(String key) {
        if (!isExists(key)) throw new JFExpection("", key);
        return classes.get(key);
    }

    public static void set(String key, ClassValue classDef) {
        classes.put(key, classDef);
    }
}