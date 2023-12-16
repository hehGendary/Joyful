package AST.Expressions;

import AST.Values.NumberValue;
import AST.Values.AbstractValue;
import Visitors.Visitor;

public final class TripleExpression implements AbstractExpression {

    public final AbstractExpression expr1, expr2, expr3;
    private final char operation;

    private double tod(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    private String tos(double dou) {
        return String.valueOf(dou);
    }

    public TripleExpression(char operation, AbstractExpression expr1, AbstractExpression expr2, AbstractExpression expr3) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.expr3 = expr3;
    }

    @Override
    public String eval() {
        return tos(valEval().asDouble());
    }

    @Override
    public AbstractValue valEval() {
        float first = expr1.valEval().asNum();
        float second = expr2.valEval().asNum();
        float third = expr3.valEval().asNum();
        switch (operation) {
            default: return new NumberValue((first == 1) ? second : third);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %c %s", expr1, operation, expr2);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}