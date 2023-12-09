
package AST;

public class VariableExpression implements Expression {
    private String tos(double dou) {
        return String.valueOf(dou);
    }

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public String eval() {
        if (!Variables.isExists(name)) throw new RuntimeException("Constant does not exists");
        return tos(Variables.get(name).asDouble());
    }

    public Value valEval() {
        if (!Variables.isExists(name)) throw new RuntimeException("Constant does not exists");
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}