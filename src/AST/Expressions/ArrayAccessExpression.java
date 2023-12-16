package AST.Expressions;

import AST.Library.Variables.Variables;
import AST.Values.ArrayValue;
import AST.Values.AbstractValue;
import Visitors.Visitor;

public final class ArrayAccessExpression implements AbstractExpression {

    private final String variable;
    private final AbstractExpression index;


    public ArrayAccessExpression(String variable, AbstractExpression index) {
        this.variable = variable;
        this.index = index;
    }

    @Override
    public AbstractValue valEval() {
        final AbstractValue var = Variables.get(variable);
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

}