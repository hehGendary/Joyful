package AST.Statements;

import AST.Expressions.FunctionalExpression;
import Visitors.Visitor;

public final class FunctionStatement implements AbstractStatement {

    public final FunctionalExpression function;

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