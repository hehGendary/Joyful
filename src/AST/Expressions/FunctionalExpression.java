package AST.Expressions;

import AST.Library.Funcs.Function;
import AST.Library.Funcs.Functions;
import AST.Library.Funcs.UserDefinedFunction;
import AST.Library.Variables.Variables;
import AST.Values.AbstractValue;
import Visitors.ResultVisitor;
import Visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public final class FunctionalExpression implements Expression {

    private final String name;
    public final List<Expression> arguments;

    public FunctionalExpression(String name) {
        this.name = name;
        arguments = new ArrayList<>();
    }

    public FunctionalExpression(String name, List<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public void addArgument(Expression arg) {
        arguments.add(arg);
    }

    @Override
    public AbstractValue valEval() {
        final int size = arguments.size();
        final AbstractValue[] values = new AbstractValue[size];
        for (int i = 0; i < size; i++) {
            values[i] = arguments.get(i).valEval();
        }

        final Function function = Functions.get(name);
        if (function instanceof UserDefinedFunction) {
            final UserDefinedFunction userFunction = (UserDefinedFunction) function;
            if (size != userFunction.getArgsCount()) throw new RuntimeException("Args count mismatch");

            Variables.push();
            for (int i = 0; i < size; i++) {
                Variables.set(userFunction.getArgsName(i), values[i]);
            }
            final AbstractValue result = userFunction.execute(values);
            Variables.pop();
            return result;
        }
        return function.execute(values);
    }

    @Override
    public String eval() {
        return name + "(" + arguments.toString() + ")";
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