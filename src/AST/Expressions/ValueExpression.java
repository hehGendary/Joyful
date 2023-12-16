package AST.Expressions;

import AST.Values.NumberValue;
import AST.Values.StringValue;
import AST.Values.Value;
import Visitors.Visitor;

public final class ValueExpression implements AbstractExpression {

    public final Value value;

    public ValueExpression(double value) {
        this.value = new NumberValue(value);
    }

    public ValueExpression(String value) {
        this.value = new StringValue(value);
    }

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public String eval() {
        return value.asStr();
    }

    public Value valEval() {
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