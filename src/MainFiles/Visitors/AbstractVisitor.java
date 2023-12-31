package MainFiles.Visitors;

import MainFiles.AST.Expressions.*;
import MainFiles.AST.Statements.*;

public abstract class AbstractVisitor implements Visitor {
    public void visit(ArrayExpression s) {

    }
    public void visit(BinaryExpression s) {
        s.expr1.accept(this);
        s.expr2.accept(this);
    }
    public void visit(FunctionalExpression s) {
        for (Expression e : s.exprArgs) {
            e.accept(this);
        }
    }
    public void visit(TripleExpression s) {
        s.expr1.accept(this);
        s.expr2.accept(this);
        s.expr3.accept(this);
    }
    public void visit(UnaryExpression s) {
        s.expr1.accept(this);
    }
    public void visit(ValueExpression s) {

    }
    public void visit(VariableExpression s) {

    }
    public void visit(ArrayAssignmentStatement s) {
        s.expression.accept(this);
    }
    public void visit(BlockStatement s) {
        for (Statement st : s.statements) {
            st.accept(this);
        }
    }

    public void visit(ClassDeclarationStatement s) {

    }
    public void visit(ForStatement s) {
        s.initialization.accept(this);
        s.termination.accept(this);
        s.increment.accept(this);
        s.statement.accept(this);
    }
    public void visit(FunctionDefineStatement s) {
        s.body.accept(this);
    }
    public void visit(FunctionStatement s) {
        s.function.accept(this);
    }
    public void visit(ifElseStatement s) {
        s.expression.accept(this);
        s.ifStatement.accept(this);
        if (s.elseStatement != null) s.elseStatement.accept(this);
    }
    public void visit(makeVariableStatement s) {
        s.expression.accept(this);
    }
    public void visit(PrintStatement s) {
        s.expr.accept(this);
    }
    public void visit(ReturnStatement s) {
        s.expression.accept(this);
    }
    public void visit(WhileStatement s) {
        s.condition.accept(this);
        s.statement.accept(this);
    }

    @Override
    public void visit(TryMistakeStatement s) {
        s.trySt.accept(this);
        s.misSt.accept(this);
    }

    public void visit(MorjExpression s) {
        
    }
}