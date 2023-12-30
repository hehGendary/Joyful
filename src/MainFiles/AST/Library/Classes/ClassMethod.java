package MainFiles.AST.Library.Classes;

import MainFiles.AST.Library.Funcs.Args.Arguments;
import MainFiles.AST.Library.Funcs.UserDefinedFunction;
import MainFiles.AST.Statements.Statement;
import MainFiles.AST.Values.Value;
import MainFiles.AST.Library.Variables.Variables;

import java.io.IOException;

public class ClassMethod extends UserDefinedFunction {

    public final ClassValue classInstance;

    public ClassMethod(Arguments arguments, Statement body, ClassValue classInstance) {
        super(arguments, body);
        this.classInstance = classInstance;
    }

    @Override
    public Value execute(Value[] values) throws IOException {
        Variables.push();
        Variables.set("this", (Value) classInstance);

        try {
            return super.execute(values);
        } finally {
            Variables.pop();
        }
    }
}