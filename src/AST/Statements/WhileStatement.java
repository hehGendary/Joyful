package AST.Statements;
import AST.*;
import AST.Expressions.Expression;
import AST.Visitors.Visitor;

public final class WhileStatement implements Statement {

    private final Expression condition;
    private final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {
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
}