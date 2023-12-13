package AST.Statements;

import AST.Expressions.FunctionalExpression;

public final class FunctionStatement implements Statement {

    private final FunctionalExpression function;

    public FunctionStatement(FunctionalExpression function) {
        this.function = function;
    }

    @Override
    public void execute() {
        function.valEval();
    }

    @Override
    public String toString() {
        return function.toString();
    }
}