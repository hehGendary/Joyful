package AST.Statements;

import AST.Expressions.AbstractExpression;
import AST.Values.Value;
import Visitors.Visitor;

public final class ReturnStatement extends RuntimeException implements AbstractStatement {

    public final AbstractExpression expression;
    private Value result;

    public ReturnStatement(AbstractExpression expression) {
        this.expression = expression;
    }

    public Value getResult() {
        return result;
    }

    @Override
    public void execute() {
        result = expression.valEval();
        throw this;
    }

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}