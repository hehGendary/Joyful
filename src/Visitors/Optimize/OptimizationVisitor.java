package Visitors.Optimize;

import AST.Library.Nodes.Node;
import AST.Expressions.*;
import AST.Statements.*;
import Visitors.ResultVisitor;

import java.util.ArrayList;
import java.util.List;

public abstract class OptimizationVisitor<T> implements ResultVisitor<Node, T> {
    @Override
    public Node visit(ArrayExpression s, T t) {
        final List<Expression> elements = new ArrayList<>(s.elements.size());
        boolean changed = false;

        for (Expression expression : s.elements) {
            Node node = expression.accept(this, t);
            if (node != expression) {
                changed = true;
            }
            elements.add((Expression) node);
        }
        if (changed) return new ArrayExpression(elements);
        return s;
    }

    @Override
    public Node visit(BinaryExpression s, T t) {
        Node expr1 = s.expr1.accept(this, t);
        Node expr2 = s.expr2.accept(this, t);
        if (expr1 != s.expr1 || expr2 != s.expr2) {
            return new BinaryExpression(s.operation, (Expression) expr1,
                    (Expression) s.expr2);
        }
        return s;
    }

    @Override
    public Node visit(BlockStatement s, T t) {
         boolean changed = false;
         BlockStatement result = new BlockStatement();

         for (Statement statement : s.statements) {
            Node node = statement.accept(this, t);

            if (node != statement) changed = true;
            result.add((Statement) node);
         }

        if (changed) return result;

        return s;
    }

    @Override
    public Node visit(ForStatement s, T t) {
        final Node initialization = s.initialization.accept(this, t);
        final Node termination = s.termination.accept(this, t);
        final Node increment = s.increment.accept(this, t);
        final Node statement = s.statement.accept(this, t);
        if (initialization != s.initialization || termination != s.termination
                || increment != s.increment || statement != s.statement) {
            return new ForStatement(consumeSt(initialization),
                    (Expression) termination, consumeSt(increment),
                    consumeSt(statement));
        }
        return s;
    }

    @Override
    public Node visit(ifElseStatement s, T t) {
        final Node expression = s.expression.accept(this, t);
        final Node ifStatement = s.ifStatement.accept(this, t);
        final Node elseStatement;
        if (s.elseStatement != null) {
            elseStatement = s.elseStatement.accept(this, t);
        } else {
            elseStatement = null;
        }
        if (expression != s.expression || ifStatement != s.ifStatement || elseStatement != s.elseStatement) {
            return new ifElseStatement((Expression) expression, consumeSt(ifStatement),
                    (elseStatement == null ? null : consumeSt(elseStatement)));
        }
        return s;
    }

    public Node visit(ReturnStatement s, T t) {
        final Node expression = s.expression.accept(this, t);
        if (expression != s.expression) {
            return new ReturnStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(UnaryExpression s, T t) {
        final Node expr1 = s.expr1.accept(this, t);
        if (expr1 != s.expr1) {
            return new UnaryExpression(s.operation, (Expression) expr1);
        }
        return s;
    }

    @Override
    public Node visit(VariableExpression s, T t) {
        return s;
    }

    @Override
    public Node visit(ValueExpression s, T t) {
        return s;
    }

    @Override
    public Node visit(WhileStatement s, T t) {
        final Node condition = s.condition.accept(this, t);
        final Node statement = s.statement.accept(this, t);
        if (condition != s.condition || statement != s.statement) {
            return new WhileStatement((Expression) condition, consumeSt
                    (statement));
        }
        return s;
    }

    protected Statement consumeSt(Node node) {
        return (Statement) node;
    }
}