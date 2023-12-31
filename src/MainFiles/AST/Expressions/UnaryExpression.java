package MainFiles.AST.Expressions;

import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.Value;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;

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
    public String eval() throws IOException {
        switch (operation) {
            case '-': return "-" + expr1.eval();
            case '!': return "!" + expr1.eval();
            case '+':
            default:
                return expr1.eval();
        }
    }

    @Override
    public Value valEval() throws IOException {
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