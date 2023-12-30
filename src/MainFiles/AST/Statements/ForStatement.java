package MainFiles.AST.Statements;

import MainFiles.AST.Expressions.Expression;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;

public final class ForStatement implements Statement {

    public final Statement initialization;
    public final Expression termination;
    public final Statement increment;
    public final Statement statement;

    public ForStatement(Statement initialization, Expression termination, Statement increment, Statement block) {
        this.initialization = initialization;
        this.termination = termination;
        this.increment = increment;
        this.statement = block;
    }

    @Override
    public void execute() throws IOException {
        for (initialization.execute(); termination.valEval().asNum() != 0; increment.execute()) {
            statement.execute();
        }
    }

    @Override
    public String toString() {
        return "for " + initialization + ", " + termination + ", " + increment + " " + statement;
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