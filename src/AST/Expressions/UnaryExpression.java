package AST.Expressions;

import AST.Values.NumberValue;
import AST.Values.Value;
import Visitors.ResultVisitor;
import Visitors.Visitor;

public final class UnaryExpression implements Expression {

    public final Expression expr1;
    public final char operation;

    private double tod(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    public UnaryExpression(char operation, Expression expr1) {
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
    public Value valEval() {
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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}