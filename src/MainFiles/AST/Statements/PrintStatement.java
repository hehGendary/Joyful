package MainFiles.AST.Statements;

import MainFiles.AST.Expressions.Expression;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;

public class PrintStatement implements Statement {
    public Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    public void execute() throws IOException {
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
