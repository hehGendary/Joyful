package Visitors;

import AST.Expressions.*;
import AST.Statements.*;

public class FunctionAdder extends AbstractVisitor {
    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {

    }

}