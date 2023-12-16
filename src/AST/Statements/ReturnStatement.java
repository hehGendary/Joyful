package AST.Statements;

import AST.Expressions.Expression;
import AST.Values.AbstractValue;
import Visitors.ResultVisitor;
import Visitors.Visitor;

public final class ReturnStatement extends RuntimeException implements Statement {

    public final Expression expression;
    private AbstractValue result;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public AbstractValue getResult() {
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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}