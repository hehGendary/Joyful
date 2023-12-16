package AST.Statements;
import AST.Expressions.AbstractExpression;
import Visitors.Visitor;

public final class WhileStatement implements AbstractStatement {

    public final AbstractExpression condition;
    public final AbstractStatement statement;

    public WhileStatement(AbstractExpression condition, AbstractStatement statement) {
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