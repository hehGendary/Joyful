
package MainFiles.AST.Expressions;

import MainFiles.AST.Library.Variables.Variables;
import MainFiles.AST.Values.Value;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;

public class VariableExpression implements Expression {
    private String tos(double dou) {
        return String.valueOf(dou);
    }

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public String eval() throws IOException {
        return null;
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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}