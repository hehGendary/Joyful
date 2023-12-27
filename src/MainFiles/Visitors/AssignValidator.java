package MainFiles.Visitors;

import MainFiles.AST.Expressions.ArrayAccessExpression;
import MainFiles.AST.Statements.UseStatement;
import MainFiles.AST.Statements.makeVariableStatement;

public class AssignValidator extends AbstractVisitor {

    @Override
    public void visit(makeVariableStatement s) {
        super.visit(s);
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {

    }

    @Override
    public void visit(UseStatement s) {

    }
}