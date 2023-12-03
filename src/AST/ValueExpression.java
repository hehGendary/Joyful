package AST;

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
    public double eval() {
        return value.asDouble();
    }

    @Override
    public String toString() {
        return value.asStr();
    }
}