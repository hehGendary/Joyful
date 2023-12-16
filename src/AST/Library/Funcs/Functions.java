package AST.Library.Funcs;
import AST.Values.ArrayValue;
import AST.Values.NumberValue;
import AST.Values.StringValue;
import AST.Values.AbstractValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class Functions {

    private static final Map<String, Function> functions;

    static {
        functions = new HashMap<>();
        functions.put("sin",    new Function() {

            @Override
            public AbstractValue execute(AbstractValue... args) {
                if (args.length != 1) throw new RuntimeException("One arg expected");
                return new NumberValue(Math.sin(args[0].asNum()));
            }
        });
        functions.put("cos", (Function) (AbstractValue... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new NumberValue(Math.cos(args[0].asNum()));
        });
        functions.put("array", (Function) (AbstractValue... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new ArrayValue((int)args[0].asNum());
        });
        functions.put("arrlen", (Function) (AbstractValue... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new NumberValue(args[0].arrLen());
        });

        functions.put("strlen", (Function) (AbstractValue... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new NumberValue(args[0].asStr().length());
        });

        functions.put("char", (Function) (AbstractValue... args) -> {
            if (args.length != 2) throw new RuntimeException("");
            return new StringValue(String.format("%c", args[0].asStr().charAt((int)args[1].asNum())));
        });

        functions.put("numToChar", (Function) (AbstractValue... args) -> {
            if (args.length != 1) throw new RuntimeException("");
            return new StringValue(String.format("%c", (char)args[0].asNum()));
        });

        functions.put("stringToNum", (Function) (AbstractValue... args) -> {
            if (args.length != 1) throw new RuntimeException("");
            return new NumberValue(Double.parseDouble(args[0].asStr()));
        });

        functions.put("addStr", (Function) (AbstractValue... args) -> {
            if (args.length != 2) throw new RuntimeException("");
            return new StringValue(args[0].asStr() + args[1].asStr());
        });

        functions.put("input", (Function) (AbstractValue... args) -> {
            if (args.length != 1) throw new RuntimeException("");
            Scanner sc = new Scanner(System.in);
            System.out.print(args[0].asStr());
            return new StringValue(sc.nextLine());
        });

        functions.put("println", args -> {
            for (AbstractValue arg : args) {
                System.out.println(arg.asStr());
            }
            return new NumberValue(0);
        });
        functions.put("print", args -> {
            for (AbstractValue arg : args) {
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