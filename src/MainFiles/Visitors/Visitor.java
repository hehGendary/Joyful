package MainFiles.Visitors;

import MainFiles.AST.Expressions.*;
import MainFiles.AST.Library.Nodes.Node;
import MainFiles.AST.Statements.*;

public interface Visitor {
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

    void visit(ClassDeclarationStatement classDeclarationStatement);

    void visit(ArrayAccessExpression arrayAccessExpression);
    void visit(UseStatement s);
    void visit(TryMistakeStatement s);
    void visit(MorjExpression s);

    void visit(Node s);
}