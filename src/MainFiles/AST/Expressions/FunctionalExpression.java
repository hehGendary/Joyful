package MainFiles.AST.Expressions;

import MainFiles.AST.Library.Funcs.Args.Arguments;
import MainFiles.AST.Library.Funcs.Function;
import MainFiles.AST.Library.Funcs.Functions;
import MainFiles.AST.Library.Funcs.UserDefinedFunction;
import MainFiles.AST.Library.Variables.Variables;
import MainFiles.AST.Values.Value;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FunctionalExpression implements Expression {

    private final String name;
    public final List<Expression> exprArgs;
    public Arguments args;

    public FunctionalExpression(String name) {
        this.name = name;
        exprArgs = new ArrayList<>();
        args = null;
    }

    public FunctionalExpression(String name, List<Expression> arguments) {
        this.name = name;
        this.exprArgs = arguments;
    }

    public FunctionalExpression(String name, Arguments arguments) {
        this.name = name;
        args = arguments;
        exprArgs = null;
    }

    public void addArgument(Expression arg) {
        exprArgs.add(arg);
    }

    @Override
    public String eval() throws IOException {
        return null;
    }

    @Override
    public Value valEval() throws IOException {
        final int size = exprArgs.size();
        final Value[] values = new Value[size];
        if (exprArgs != null) {
            for (int i = 0; i < size; i++) {
                values[i] = exprArgs.get(i).valEval();
            }
        } else {
            for (int i = 0; i < size; i++) {
                values[i] = args.get(i).getValueExpr().valEval();
            }
        }

        final Function function = Functions.get(name);
        if (function instanceof UserDefinedFunction) {
            final UserDefinedFunction userFunction = (UserDefinedFunction) function;
            if (size != userFunction.getArgsCount()) throw new RuntimeException("Args count mismatch");

            Variables.push();
            for (int i = 0; i < size; i++) {
                Variables.define(userFunction.getArgsName(i), values[i]);
            }
            final Value result = userFunction.execute(values);
            Variables.pop();
            return result;
        }
        return function.execute(values);
    }
    @Override
    public String toString() {
        return name + "(" + exprArgs.toString() + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}