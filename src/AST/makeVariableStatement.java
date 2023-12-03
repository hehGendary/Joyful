package AST;

public final class makeVariableStatement implements Statement {

    private final String variable;
    private final Expression expression;

    public makeVariableStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final double result = expression.eval();
        if (result != 0) {
            Variables.set(variable, new NumberValue(result));
        }
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
    }
}