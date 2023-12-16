package AST.Statements;

import AST.Expressions.AbstractExpression;
import Visitors.Visitor;

public final class ifElseStatement implements AbstractStatement {

    public final AbstractExpression expression;
    public final AbstractStatement ifStatement, elseStatement;

    public ifElseStatement(AbstractExpression expression, AbstractStatement ifStatement, AbstractStatement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        final double result = expression.valEval().asDouble();
        if (result != 0) {
            ifStatement.execute();
        } else if (elseStatement != null) {
            elseStatement.execute();
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("if ").append(expression).append(' ').append(ifStatement);
        if (elseStatement != null) {
            result.append("\nelse ").append(elseStatement);
        }
        return result.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}