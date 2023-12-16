package AST.Library.Funcs;

import AST.Statements.ReturnStatement;
import AST.Statements.Statement;
import AST.Values.NumberValue;
import AST.Values.AbstractValue;

import java.util.List;

public final class UserDefinedFunction implements Function {

    private final List<String> argNames;
    private final Statement body;

    public UserDefinedFunction(List<String> argNames, Statement body) {
        this.argNames = argNames;
        this.body = body;
    }

    public int getArgsCount() {
        return argNames.size();
    }

    public String getArgsName(int index) {
        if (index < 0 || index >= getArgsCount()) return "";
        return argNames.get(index);
    }

    @Override
    public AbstractValue execute(AbstractValue... args) {
        try {
            body.execute();
            return new NumberValue(0);
        } catch (ReturnStatement rt) {
            return rt.getResult();
        }
    }
}