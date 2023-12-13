package AST.Library;
import AST.Values.NumberValue;
import AST.Values.Value;

import java.util.HashMap;
import java.util.Map;

public final class Functions {

    private static final Map<String, Function> functions;

    static {
        functions = new HashMap<>();
        functions.put("sin", new Function() {

            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One arg expected");
                return new NumberValue(Math.sin(args[0].asNum()));
            }
        });
        functions.put("cos", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new NumberValue(Math.cos(args[0].asNum()));
        });
        functions.put("println", args -> {
            for (Value arg : args) {
                System.out.println(arg.asStr());
            }
            return new NumberValue(0);
        });
        functions.put("print", args -> {
            for (Value arg : args) {
                System.out.print(arg.asStr());
            }
            return new NumberValue(0);
        });
    }

    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }

    public static Function get(String key) {
        if (!isExists(key)) throw new RuntimeException("Unknown function " + key);
        return functions.get(key);
    }

    public static void set(String key, Function function) {
        functions.put(key, function);
    }
}