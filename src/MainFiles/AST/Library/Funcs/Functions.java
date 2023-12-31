package MainFiles.AST.Library.Funcs;
import MainFiles.AST.Values.ArrayValue;
import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.StringValue;
import MainFiles.AST.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class Functions {

    private static final Map<String, Function> functions;

    static {
        functions = new HashMap<>();
        functions.put("sin",    new Function() {

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
        functions.put("array", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new ArrayValue((int)args[0].asNum());
        });
        functions.put("arrlen", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new NumberValue(args[0].arrLen());
        });

        functions.put("strlen", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("One arg expected");
            return new NumberValue(args[0].asStr().length());
        });

        functions.put("char", (Function) (Value... args) -> {
            if (args.length != 2) throw new RuntimeException("");
            return new StringValue(String.format("%c", args[0].asStr().charAt((int)args[1].asNum())));
        });

        functions.put("numToChar", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("");
            return new StringValue(String.format("%c", (char)args[0].asNum()));
        });

        functions.put("stringToNum", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("");
            return new NumberValue(Double.parseDouble(args[0].asStr()));
        });

        functions.put("numToStr", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("");
            return new StringValue(String.valueOf(args[0].asNum()));
        });


        functions.put("addStr", (Function) (Value... args) -> {
            return new StringValue(args[0].asStr() + args[1].asStr());
        });

        functions.put("StrToArr", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("");

            int strLen = args[0].asStr().length();
            ArrayValue arr = new ArrayValue(strLen);
            for (int i = 0; i < strLen; i++) {
                arr.set(i, new StringValue(String.valueOf(args[0].asStr().charAt(i))));
            }

            return arr;
        });

        functions.put("ArrToStr", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("");

            StringBuilder sb = new StringBuilder();
            int arrLen = args[0].arrLen();

            for (int i = 0; i < arrLen; i++) {
                sb.append(((ArrayValue)args[0]).get(i).asStr());
            }

            return new StringValue(sb.toString());
        });


        functions.put("input", (Function) (Value... args) -> {
            if (args.length != 1) throw new RuntimeException("");
            Scanner sc = new Scanner(System.in);
            System.out.print(args[0].asStr());
            return new StringValue(sc.nextLine());
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

    public static void define(String key, Function function) {
        functions.put(key, function);
    }

    public static void set(String key, Function function) {
        functions.put(key, function);
    }
}