package AST.Statements;

import AST.Library.Funcs.Functions;
import AST.Library.Funcs.UserDefinedFunction;
import Visitors.Visitor;

import java.util.List;

public final class FunctionDefineStatement implements AbstractStatement {

    private final String name;
    private final List<String> argNames;
    public final AbstractStatement body;

    public FunctionDefineStatement(String name, List<String> argNames, AbstractStatement body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
    }

    @Override
    public void execute() {
        Functions.set(name, new UserDefinedFunction(argNames, body));
    }

    @Override
    public String toString() {
        return "def (" + argNames.toString() + ") " + body.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

