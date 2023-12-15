package AST.Statements;

import AST.Expressions.FunctionalExpression;
import AST.Visitors.Visitor;

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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}