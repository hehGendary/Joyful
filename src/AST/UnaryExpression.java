package AST;

public final class UnaryExpression implements Expression {

    private final Expression expr1;
    private final char operation;

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
            case '+':
            default:
                return expr1.eval();
        }
    }

    @Override
    public Value valEval() {
        switch (operation) {
            case '-': return new NumberValue(tod("-" + expr1.eval()));
            case '+':
            default:
                return new NumberValue(tod(expr1.eval()));
        }
    }

    @Override
    public String toString() {
        return String.format("%c %s", operation, expr1);
    }
}