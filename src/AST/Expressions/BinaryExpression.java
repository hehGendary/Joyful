package AST.Expressions;

import AST.Values.NumberValue;
import AST.Values.Value;
import Visitors.Visitor;

public final class BinaryExpression implements Expression {

    public final Expression expr1, expr2;
    private final char operation;

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
    public String eval() {
        return tos(valEval().asDouble());
    }

    @Override
    public Value valEval() {
        float first = expr1.valEval().asNum();
        float second = expr2.valEval().asNum();
        switch (operation) {

            case '+': return new NumberValue((double)first + second);
            case '*': return new NumberValue((double)first * second);
            case '/': return new NumberValue((double)first / second);
            case '%': return new NumberValue((double)first % second);
            case '&': return new NumberValue((first == 1 && second == 1) ? 1 : 0);
            case '|': return new NumberValue((first == 1 || second == 1) ? 1 : 0);
            case '=': return new NumberValue((first == second) ? 1 : 0);
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
}