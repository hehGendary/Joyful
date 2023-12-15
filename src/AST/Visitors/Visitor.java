package AST.Visitors;

import AST.Expressions.*;
import AST.Statements.*;

public interface Visitor {
    void visit(ArrayAccessExpression s);
    void visit(ArrayExpression s);
    void visit(BinaryExpression s);
    void visit(FunctionalExpression s);
    void visit(TripleExpression s);
    void visit(UnaryExpression s);
    void visit(ValueExpression s);
    void visit(VariableExpression s);
    void visit(ArrayAssignmentStatement s);
    void visit(BlockStatement s);
    void visit(ForStatement s);
    void visit(FunctionDefineStatement s);
    void visit(FunctionStatement s);
    void visit(ifElseStatement s);
    void visit(makeVariableStatement s);
    void visit(PrintStatement s);
    void visit(ReturnStatement s);
    void visit(WhileStatement s);
}