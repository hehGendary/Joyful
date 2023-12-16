package AST.Expressions;

import AST.Values.NumberValue;
import AST.Values.AbstractValue;
import Visitors.Visitor;

public final class UnaryExpression implements AbstractExpression {

    public final AbstractExpression expr1;
    private final char operation;

    private double tod(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    public UnaryExpression(char operation, AbstractExpression expr1) {
        this.operation = operation;
        this.expr1 = expr1;
    }

    @Override
    public String eval() {
        switch (operation) {
            case '-': return "-" + expr1.eval();
            case '!': return "!" + expr1.eval();
            case '+':
            default:
                return expr1.eval();
        }
    }

    @Override
    public AbstractValue valEval() {
        switch (operation) {
            case '-': return new NumberValue(tod("-" + expr1.eval()));
            case '!': return new NumberValue(expr1.valEval().asNum() == 0.0
                    ? 1 : 0);
            case '+':
            default:
                return new NumberValue(tod(expr1.eval()));
        }
    }

    @Override
    public String toString() {
        return String.format("%c %s", operation, expr1);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}