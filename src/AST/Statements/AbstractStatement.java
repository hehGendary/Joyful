package AST.Statements;

import AST.Library.Nodes.Node;

public interface AbstractStatement extends Node {
    void execute();
}