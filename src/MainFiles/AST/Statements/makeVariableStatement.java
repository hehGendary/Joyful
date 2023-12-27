package MainFiles.AST.Statements;

import MainFiles.AST.Expressions.Expression;
import MainFiles.AST.Library.Variables.Variables;
import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.Value;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

public final class makeVariableStatement implements Statement {

    private double tod(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    private String tos(double dou) {
        return String.valueOf(dou);
    }
    public final String variable;
    public final Expression expression;

    public makeVariableStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final Value result = expression.valEval();
        if (result != new NumberValue(0)) {
            Variables.set(variable, result);
        }
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
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