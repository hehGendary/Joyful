
package AST;

public class VariableExpression implements Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public double eval() {
        if (!Variables.isExists(name)) throw new RuntimeException("Constant does not exists");
        return Variables.get(name).asDouble();
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}