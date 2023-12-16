package AST.Expressions;

import AST.Library.Nodes.Node;
import AST.Values.AbstractValue;

public interface Expression extends Node {
    String eval();

    AbstractValue valEval();

}