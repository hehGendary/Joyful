package MainFiles.AST.Library.Funcs;

import MainFiles.AST.Values.Value;

public interface Function {

    Value execute(Value... args);
}