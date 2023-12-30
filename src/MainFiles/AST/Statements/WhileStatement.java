package MainFiles.AST.Statements;
import MainFiles.AST.Expressions.Expression;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;

public final class WhileStatement implements Statement {

    public final Expression condition;
    public final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() throws IOException {
        while (condition.valEval().asNum() != 0) {
            statement.execute();
        }
    }

    @Override
    public String toString() {
        return "while " + condition + " " + statement;
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