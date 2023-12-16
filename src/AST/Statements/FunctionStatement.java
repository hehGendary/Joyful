package AST.Statements;

import AST.Expressions.FunctionalExpression;
import Visitors.ResultVisitor;
import Visitors.Visitor;

public final class FunctionStatement implements Statement {

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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}