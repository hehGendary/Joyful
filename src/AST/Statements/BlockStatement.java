package AST.Statements;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {
    List<Statement> statements;

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
}