package AST.Expressions;

import AST.Library.Node;
import AST.Values.Value;

public interface Expression extends Node {
    String eval();

    Value valEval();
}