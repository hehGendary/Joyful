package AST.Values;

public final class ArrayValue implements AbstractValue {

    public final AbstractValue[] elements;

    public ArrayValue(int size) {
        this.elements = new AbstractValue[size];
    }

    @Override
    public int arrLen() {
        return elements.length;
    }

    public ArrayValue(AbstractValue[] elements) {
        this.elements = new AbstractValue[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    public ArrayValue(ArrayValue array) {
        this(array.elements);
    }

    public AbstractValue get(int index) {
        return elements[index];
    }

    public void set(int index, AbstractValue value) {
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
        for (AbstractValue v : elements) {
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