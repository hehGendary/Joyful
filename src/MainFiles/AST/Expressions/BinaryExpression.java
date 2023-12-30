package MainFiles.AST.Expressions;

import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.Value;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;
import java.util.Objects;

public final class BinaryExpression implements Expression {

    public final Expression expr1, expr2;
    public final char operation;

    private double tod(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    private String tos(double dou) {
        return String.valueOf(dou);
    }

    public BinaryExpression(char operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public String eval() throws IOException {
        return tos(valEval().asDouble());
    }

    @Override
    public Value valEval() throws IOException {
        float first = expr1.valEval().asNum();
        float second = expr2.valEval().asNum();
        switch (operation) {

            case '+': return new NumberValue((double)first + second);
            case '*': return new NumberValue((double)first * second);
            case '/': return new NumberValue((double)first / second);
            case '%': return new NumberValue((double)first % second);
            case '&': return new NumberValue((first == 1 && second == 1) ? 1 : 0);
            case '|': return new NumberValue((first == 1 || second == 1) ? 1 : 0);
            case '=': return new NumberValue((Objects.equals(expr1.valEval().asStr(), expr2.valEval().asStr())) ? 1 : 0);
            case '>': return new NumberValue((first > second) ? 1 : 0);
            case '<': return new NumberValue((first < second) ? 1 : 0);
            case '-':
            default:
                return new NumberValue((double) first-second);
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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}