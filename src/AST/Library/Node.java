package AST.Library;

import AST.Visitors.Visitor;

public interface Node {
    void accept(Visitor visitor);
}