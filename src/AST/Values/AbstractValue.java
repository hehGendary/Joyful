package AST.Values;

public interface AbstractValue {
    float asNum();
    double asDouble();

    int arrLen();

    String asStr();
}