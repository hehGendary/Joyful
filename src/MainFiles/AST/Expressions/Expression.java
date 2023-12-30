package MainFiles.AST.Expressions;

import MainFiles.AST.Library.Nodes.Node;
import MainFiles.AST.Values.Value;

import java.io.IOException;

public interface Expression extends Node {
    String eval() throws IOException;

    Value valEval() throws IOException;
}