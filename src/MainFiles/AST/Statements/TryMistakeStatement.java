package MainFiles.AST.Statements;

import MainFiles.AST.Statements.Statement;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

public class TryMistakeStatement implements Statement {

    public Statement trySt;
    public Statement misSt;

    public TryMistakeStatement(Statement t, Statement m) {
        trySt = t;
        misSt = m;
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }

    @Override
    public void execute() {
        try {
            trySt.execute();
        } catch (Exception e) {
            misSt.execute();
        }
    }
}