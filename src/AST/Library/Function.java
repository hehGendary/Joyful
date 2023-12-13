package AST.Library;

import AST.Values.Value;

public interface Function {

    Value execute(Value... args);
}