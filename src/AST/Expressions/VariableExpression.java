
package AST.Expressions;

import AST.Library.Variables;
import AST.Values.Value;
import Visitors.Visitor;

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
        if (!Variables.isExists(name)) throw new RuntimeException(
                String.format("Constant %s does not exists", name));
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}