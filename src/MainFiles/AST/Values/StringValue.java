package MainFiles.AST.Values;

public class StringValue implements Value {
    public final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public float asNum() {
        return 0;
    }

    public int arrLen() {
        return 0;
    }

    @Override
    public String asStr() {
        return value;
    }

    @Override
    public double asDouble() {
        return 0;
    }
}