package AST.Library.Funcs;

import AST.Library.Funcs.Args.Arguments;
import AST.Library.Nodes.Node;
import AST.Statements.ReturnStatement;
import AST.Statements.Statement;
import AST.Values.NumberValue;
import AST.Values.Value;

import java.util.ArrayList;
import java.util.List;

public class UserDefinedFunction implements Function {

    private final List<String> argNames;
    private final Statement body;

    public UserDefinedFunction(List<String> argNames, Statement body) {
        this.argNames = argNames;
        this.body = body;
    }

    public UserDefinedFunction(Arguments args, Node body) {
        int size = args.getRequiredArgumentsCount();
        List<String> argNames = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            argNames.set(i, "");
        }

        this.argNames = argNames;
        this.body = (Statement) body;
    }

    public int getArgsCount() {
        return argNames.size();
    }

    public String getArgsName(int index) {
        if (index < 0 || index >= getArgsCount()) return "";
        return argNames.get(index);
    }

    @Override
    public Value execute(Value... args) {
        try {
            body.execute();
            return new NumberValue(0);
        } catch (ReturnStatement rt) {
            return rt.getResult();
        }
    }
}