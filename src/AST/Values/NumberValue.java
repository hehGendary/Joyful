package AST.Values;

public class NumberValue implements AbstractValue {
    float value;

    public NumberValue(double value) {
        this.value = (float)value;
    }

    @Override
    public int arrLen() {
        return 0;
    }

    public float asNum() {
        return value;
    }

    @Override
    public double asDouble() {
        return value;
    }

    public String asStr() {
        return String.format("%1$,.2f", value);
    }

}