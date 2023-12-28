package MainFiles.AST.Library.Funcs.Modules;

import MainFiles.AST.JFExpection;
import MainFiles.AST.Library.Funcs.Function;
import MainFiles.AST.Library.Funcs.Functions;
import MainFiles.AST.Values.ArrayValue;
import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.StringValue;
import MainFiles.AST.Values.Value;

public final class convert implements Module {
    public static void initConstants() {

    }

    public static void init() {
        initConstants();
        Functions.set("num", (Value... args) -> {
            Value arg = args[0];
            if (arg instanceof ArrayValue) return new NumberValue(arg.arrLen());
            if (arg instanceof StringValue) return new NumberValue(Double.parseDouble(arg.asStr()));
            return arg;
        });
        Functions.set("str", (Value... args) -> {
            Value arg = args[0];
            if (arg instanceof ArrayValue) throw new JFExpection("func", "");
            if (arg instanceof NumberValue) return new StringValue(arg.asStr());
            return arg;
        });
        Functions.set("array", (Value... args) -> {
            Value arg = args[0];
            if (arg instanceof NumberValue) throw  new JFExpection("func", "");
            if (arg instanceof StringValue) {
                int strLen = arg.asStr().length();
                ArrayValue arr = new ArrayValue(strLen);
                for (int i = 0; i < strLen; i++) {
                    arr.set(i, new StringValue(String.valueOf(arg.asStr().charAt(i))));
                }
                return arr;
            }
            return arg;
        });
    }
}