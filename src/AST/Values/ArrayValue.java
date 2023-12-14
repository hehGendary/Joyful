package AST.Values;

import java.util.Arrays;

public final class ArrayValue implements Value {

    private final Value[] elements;

    public ArrayValue(int size) {
        this.elements = new Value[size];
    }

    public ArrayValue(Value[] elements) {
        this.elements = new Value[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    public ArrayValue(ArrayValue array) {
        this(array.elements);
    }

    public Value get(int index) {
        return elements[index];
    }

    public void set(int index, Value value) {
        elements[index] = value;
    }

    @Override
    public float asNum() {
        throw new RuntimeException("Cannot cast array to number");
    }

    @Override
    public double asDouble() {
        throw new RuntimeException("Cannot cast array to number");
    }

    @Override
    public String asStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Value v : elements) {
            sb.append(v.asStr());
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return asStr();
    }
}