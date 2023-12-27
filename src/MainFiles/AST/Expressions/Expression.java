package MainFiles.AST.Expressions;

import MainFiles.AST.Library.Nodes.Node;
import MainFiles.AST.Values.Value;

public interface Expression extends Node {
    String eval();

    Value valEval();

}