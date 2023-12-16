package AST.Statements;

import Visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements AbstractStatement {
    public List<AbstractStatement> statements;

    public BlockStatement() {
        statements = new ArrayList<>();
    }

    public void add(AbstractStatement st) {
        statements.add(st);
    }

    @Override
    public void execute() {
        for (AbstractStatement st : statements) {
            st.execute();
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}