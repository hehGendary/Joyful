package AST.Library.Classes;

import AST.Library.Funcs.Args.Arguments;
import AST.Library.Funcs.UserDefinedFunction;
import AST.Statements.Statement;
import AST.Values.Value;
import AST.Library.Variables.Variables;

public class ClassMethod extends UserDefinedFunction {

    public final ClassValue classInstance;

    public ClassMethod(Arguments arguments, Statement body, ClassValue classInstance) {
        super(arguments, body);
        this.classInstance = classInstance;
    }

    @Override
    public Value execute(Value[] values) {
        Variables.push();
        Variables.set("this", (Value) classInstance);

        try {
            return super.execute(values);
        } finally {
            Variables.pop();
        }
    }
}