package AST.Library.Funcs;

import AST.Values.Value;

public interface Function {

    Value execute(Value... args);
}