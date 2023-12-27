package MainFiles.AST.Values;

public interface Value {
    float asNum();
    double asDouble();

    int arrLen();

    String asStr();
}