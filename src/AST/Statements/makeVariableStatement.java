package AST.Statements;

import AST.Expressions.AbstractExpression;
import AST.Library.Variables.Variables;
import AST.Values.NumberValue;
import AST.Values.Value;
import Visitors.Visitor;

public final class makeVariableStatement implements AbstractStatement {

    private double tod(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    private String tos(double dou) {
        return String.valueOf(dou);
    }
    public final String variable;
    public final AbstractExpression expression;

    public makeVariableStatement(String variable, AbstractExpression expression) {
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
}