package MainFiles.AST.Statements;

import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {
    public List<Statement> statements;

    public BlockStatement() {
        statements = new ArrayList<>();
    }

    public void add(Statement st) {
        statements.add(st);
    }

    @Override
    public void execute() {
        for (Statement st : statements) {
            st.execute();
        }
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