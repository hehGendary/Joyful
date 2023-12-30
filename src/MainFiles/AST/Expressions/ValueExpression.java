package MainFiles.AST.Expressions;

import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.StringValue;
import MainFiles.AST.Values.Value;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;

public final class ValueExpression implements Expression {

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
    public String eval() throws IOException {
        return null;
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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}