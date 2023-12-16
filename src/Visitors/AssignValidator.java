package Visitors;

import AST.Expressions.*;
import AST.Statements.*;

public class AssignValidator extends AbstractVisitor {

    @Override
    public void visit(makeVariableStatement s) {
        super.visit(s);
    }
}