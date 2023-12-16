package AST.Statements;

import AST.Library.Nodes.Node;

public interface Statement extends Node {
    void execute();
}