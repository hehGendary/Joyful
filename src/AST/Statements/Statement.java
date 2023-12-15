package AST.Statements;

import AST.Library.Node;

public interface Statement extends Node {
    void execute();
}