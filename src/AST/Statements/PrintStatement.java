package AST.Statements;

import AST.Expressions.Expression;
import Visitors.ResultVisitor;
import Visitors.Visitor;

public class PrintStatement implements Statement {
    public Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    public void execute() {
        System.out.println(expr.eval());
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
