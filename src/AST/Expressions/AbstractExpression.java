package AST.Expressions;

import AST.Library.Nodes.Node;
import AST.Values.AbstractValue;

public interface AbstractExpression extends Node {
    String eval();

    AbstractValue valEval();

}