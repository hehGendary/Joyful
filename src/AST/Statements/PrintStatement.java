package AST.Statements;

import AST.Expressions.AbstractExpression;
import Visitors.Visitor;

public class PrintStatement implements AbstractStatement {
    public AbstractExpression expr;

    public PrintStatement(AbstractExpression expr) {
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
