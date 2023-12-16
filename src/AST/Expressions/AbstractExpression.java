package AST.Expressions;

import AST.Library.Nodes.Node;
import AST.Values.Value;

public interface AbstractExpression extends Node {
    String eval();

    Value valEval();

}