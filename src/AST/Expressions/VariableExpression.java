
package AST.Expressions;

import AST.Library.Variables.Variables;
import AST.Values.AbstractValue;
import Visitors.ResultVisitor;
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

    public AbstractValue valEval() {
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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}