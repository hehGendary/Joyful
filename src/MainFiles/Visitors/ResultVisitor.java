package MainFiles.Visitors;

import MainFiles.AST.Expressions.*;
import MainFiles.AST.Library.Nodes.Node;
import MainFiles.AST.Statements.*;

public interface ResultVisitor<R, T> {

    R visit(ArrayExpression s, T t);
    R visit(ArrayAccessExpression s, T t);
    R visit(ArrayAssignmentStatement s, T t);
    R visit(TripleExpression s, T t);
    R visit(makeVariableStatement s, T t);
    R visit(BinaryExpression s, T t);
    R visit(BlockStatement s, T t);
    R visit(ForStatement s, T t);
    R visit(FunctionDefineStatement s, T t);
    R visit(FunctionalExpression s, T t);
    R visit(ifElseStatement s, T t);
    R visit(ClassDeclarationStatement s, T t);
    R visit(FunctionStatement s, T t);
    R visit(PrintStatement s, T t);
    R visit(ReturnStatement s, T t);
    R visit(UnaryExpression s, T t);
    R visit(ValueExpression s, T t);
    R visit(VariableExpression s, T t);
    R visit(WhileStatement s, T t);
    R visit(UseStatement s, T t);
    R visit(TryMistakeStatement s, T t);

    R visit(MorjExpression s, T t);
    R visit(Node s, T t);
}