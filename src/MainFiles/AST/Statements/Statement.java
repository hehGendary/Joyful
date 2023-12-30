package MainFiles.AST.Statements;

import MainFiles.AST.Library.Nodes.Node;

import java.io.IOException;

public interface Statement extends Node {
    void execute() throws IOException;
}