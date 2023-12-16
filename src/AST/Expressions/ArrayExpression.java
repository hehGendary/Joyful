package AST.Expressions;

import AST.Values.ArrayValue;
import AST.Values.Value;
import Visitors.Visitor;

import java.util.List;

public final class ArrayExpression implements AbstractExpression {

    private final List<AbstractExpression> elements;

    public ArrayExpression(List<AbstractExpression> arguments) {
        this.elements = arguments;
    }

    @Override
    public Value valEval() {
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
}