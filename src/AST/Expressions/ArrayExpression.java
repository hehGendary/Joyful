package AST.Expressions;

import AST.Values.ArrayValue;
import AST.Values.AbstractValue;
import Visitors.ResultVisitor;
import Visitors.Visitor;

import java.util.List;

public final class ArrayExpression implements Expression {

    public final List<Expression> elements;

    public ArrayExpression(List<Expression> arguments) {
        this.elements = arguments;
    }

    @Override
    public AbstractValue valEval() {
        final int size = elements.size();
        final ArrayValue array = new ArrayValue(size);
        for (int i = 0; i < size; i++) {
            array.set(i, elements.get(i).valEval());
        }
        return array;
    }

    @Override
    public String eval() {
        return elements.toString();
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