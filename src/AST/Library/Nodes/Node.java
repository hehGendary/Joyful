package AST.Library.Nodes;

import Visitors.Visitor;

public interface Node {
    void accept(Visitor visitor);

    int index = 0;
}