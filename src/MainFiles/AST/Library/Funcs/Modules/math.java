package MainFiles.AST.Library.Funcs.Modules.Basic;

import MainFiles.AST.Library.Funcs.Functions;
import MainFiles.AST.Library.Funcs.Modules.Module;
import MainFiles.AST.Library.Variables.Variables;
import MainFiles.AST.Values.*;

public final class math implements Module {
    private static void initConstants() {
        Variables.set("tau", new NumberValue(Math.TAU));
    }

    public static void init() {
        initConstants();
        Functions.set("sin", (Value... args) -> {
            return new NumberValue(Math.sin(args[0].asNum()));
        });
        Functions.set("cos", (Value... args) -> {
            return new NumberValue(Math.cos(args[0].asNum()));
        });
        Functions.set("tan", (Value... args) -> {
            return new NumberValue(Math.tan(args[0].asNum()));
        });
        Functions.set("cot", (Value... args) -> {
            return new NumberValue(1/Math.tan(args[0].asNum()));
        });
        Functions.set("asin", (Value... args) -> {
            return new NumberValue(Math.asin(args[0].asNum()));
        });
        Functions.set("acos", (Value... args) -> {
            return new NumberValue(Math.acos(args[0].asNum()));
        });
        Functions.set("atan", (Value... args) -> {
            return new NumberValue(Math.atan(args[0].asNum()));
        });
        Functions.set("acot", (Value... args) -> {
            return new NumberValue(1/Math.atan(args[0].asNum()));
        });
        Functions.set("floor", (Value... args) -> {
            return new NumberValue(Math.floor(args[0].asNum()));
        });
        Functions.set("ceil", (Value... args) -> {
            return new NumberValue(Math.ceil(args[0].asNum()));
        });

        Functions.set("random", (Value... args) -> {
            return new NumberValue(Math.random() * args[1].asNum() + args[0].asNum());
        });
    }
}