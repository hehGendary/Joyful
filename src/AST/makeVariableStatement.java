package AST;

public final class makeVariableStatement implements Statement {

    private double tod(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    private String tos(double dou) {
        return String.valueOf(dou);
    }
    private final String variable;
    private final Expression expression;

    public makeVariableStatement(String variable, Expression expression) {
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
}