package AST.Expressions;

import AST.Values.NumberValue;
import AST.Values.StringValue;
import AST.Values.AbstractValue;
import Visitors.Visitor;

public final class ValueExpression implements AbstractExpression {

    public final AbstractValue value;

    public ValueExpression(double value) {
        this.value = new NumberValue(value);
    }

    public ValueExpression(String value) {
        this.value = new StringValue(value);
    }

    public ValueExpression(AbstractValue value) {
        this.value = value;
    }

    @Override
    public String eval() {
        return value.asStr();
    }

    public AbstractValue valEval() {
        return value;
    }

    @Override
    public String toString() {
        return value.asStr();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}