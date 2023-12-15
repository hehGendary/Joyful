package AST.Statements;

import AST.Expressions.Expression;
import AST.Visitors.Visitor;

public final class ifElseStatement implements Statement {

    private final Expression expression;
    private final Statement ifStatement, elseStatement;

    public ifElseStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
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