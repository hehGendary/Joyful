package MainFiles.AST.Statements;

import MainFiles.AST.Expressions.Expression;
import MainFiles.AST.Library.Variables.Variables;
import MainFiles.AST.Values.ArrayValue;
import MainFiles.AST.Values.Value;
import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

import java.io.IOException;

public final class ArrayAssignmentStatement implements Statement {

    private final String variable;
    private final Expression index;
    public final Expression expression;

    public ArrayAssignmentStatement(String variable, Expression index, Expression expression) {
        this.variable = variable;
        this.index = index;
        this.expression = expression;
    }

    @Override
    public void execute() throws IOException {
        final Value var = Variables.get(variable);
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

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return visitor.visit(this, input);
    }
}