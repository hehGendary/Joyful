package MainFiles.AST.Statements;

import MainFiles.AST.Library.Nodes.Node;

public interface Statement extends Node {
    void execute();
}