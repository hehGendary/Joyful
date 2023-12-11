package AST.Statements;

import AST.Expressions.Expression;

public class PrintStatement implements Statement {
    Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    public void execute() {
        System.out.println(expr.eval());
    }
}
