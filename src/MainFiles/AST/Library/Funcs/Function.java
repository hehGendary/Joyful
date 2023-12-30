package MainFiles.AST.Library.Funcs;

import MainFiles.AST.Values.Value;

import java.io.IOException;

public interface Function {

    Value execute(Value... args) throws IOException;
}