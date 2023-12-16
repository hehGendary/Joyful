package AST.Statements;

import AST.Expressions.AbstractExpression;
import Visitors.Visitor;

public final class ForStatement implements AbstractStatement {

    public final AbstractStatement initialization;
    public final AbstractExpression termination;
    public final AbstractStatement increment;
    public final AbstractStatement statement;

    public ForStatement(AbstractStatement initialization, AbstractExpression termination, AbstractStatement increment, AbstractStatement block) {
        this.initialization = initialization;
        this.termination = termination;
        this.increment = increment;
        this.statement = block;
    }

    @Override
    public void execute() {
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
}