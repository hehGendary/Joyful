package AST.Statements;

import AST.Expressions.AbstractExpression;
import AST.Library.Variables.Variables;
import AST.Values.ArrayValue;
import AST.Values.AbstractValue;
import Visitors.Visitor;

public final class ArrayAssignmentStatement implements AbstractStatement {

    private final String variable;
    private final AbstractExpression index;
    public final AbstractExpression expression;

    public ArrayAssignmentStatement(String variable, AbstractExpression index, AbstractExpression expression) {
        this.variable = variable;
        this.index = index;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final AbstractValue var = Variables.get(variable);
        if (var instanceof ArrayValue array) {
            array.set((int) index.valEval().asNum(), expression.valEval());
        } else {
            throw new RuntimeException("Array expected");
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


    @Override
    public String toString() {
        return String.format("%s[%s] = %s", variable, index, expression);
    }
}