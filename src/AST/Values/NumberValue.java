package AST.Values;

public class NumberValue implements Value {
    double value;

    public NumberValue(double value) {
        this.value = value;
    }

    public float asNum() {
        return (float) value;
    }

    @Override
    public double asDouble() {
        return value;
    }

    public String asStr() {
        return String.format("%1$,.2f", value);
    }

}