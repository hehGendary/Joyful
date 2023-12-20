package AST.Expressions;

import AST.Library.Variables.Variables;
import AST.Values.ArrayValue;
import AST.Values.Value;
import Visitors.ResultVisitor;
import Visitors.Visitor;

public final class ArrayAccessExpression implements Expression {

    private final String variable;
    private final Expression index;


    public ArrayAccessExpression(String variable, Expression index) {
        this.variable = variable;
        this.index = index;
    }

    @Override
    public Value valEval() {
        final Value var = Variables.get(variable);
        if (var instanceof ArrayValue) {
            final ArrayValue array = (ArrayValue) var;
            return array.get((int) index.valEval().asNum());
        } else {
            throw new RuntimeException("Array expected");
        }
    }

    @Override
    public String eval() {
        return String.format("%s[%s]", variable, index);
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