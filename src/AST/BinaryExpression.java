package AST;

public final class BinaryExpression implements Expression {

    private final Expression expr1, expr2;
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
        switch (operation) {
            case '-': return tos(tod(expr1.eval()) - tod(expr2.eval()));
            case '*': return tos(tod(expr1.eval()) * tod(expr2.eval()));
            case '/': return tos(tod(expr1.eval()) / tod(expr2.eval()));
            case '+':
            default:
                return tos(tod(expr1.eval()) + tod(expr2.eval()));
        }
    }

    @Override
    public Value valEval() {
        switch (operation) {
            case '-': return new NumberValue(tod(expr1.eval()) - tod(expr2.eval()));
            case '*': return new NumberValue(tod(expr1.eval()) * tod(expr2.eval()));
            case '/': return new NumberValue(tod(expr1.eval()) / tod(expr2.eval()));
            case '+':
            default:
                return new NumberValue(tod(expr1.eval()) + tod(expr2.eval()));
        }
    }

    @Override
    public String toString() {
        return String.format("%s %c %s", expr1, operation, expr2);
    }
}