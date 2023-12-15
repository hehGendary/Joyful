package AST.Statements;

import AST.Expressions.Expression;
import AST.Visitors.Visitor;

public class PrintStatement implements Statement {
    Expression expr;

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
}
