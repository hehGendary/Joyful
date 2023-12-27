package MainFiles.Visitors;

import MainFiles.AST.Expressions.ArrayAccessExpression;
import MainFiles.AST.Statements.FunctionDefineStatement;
import MainFiles.AST.Statements.UseStatement;

public class FunctionAdder extends AbstractVisitor {
    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {

    }

    @Override
    public void visit(UseStatement s) {

    }

}