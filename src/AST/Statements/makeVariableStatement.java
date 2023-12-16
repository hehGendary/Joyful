package AST.Statements;

import AST.Expressions.Expression;
import AST.Library.Variables.Variables;
import AST.Values.NumberValue;
import AST.Values.AbstractValue;
import Visitors.ResultVisitor;
import Visitors.Visitor;

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
        final AbstractValue result = expression.valEval();
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