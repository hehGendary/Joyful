package AST.Library;

import Visitors.Visitor;

public interface Node {
    void accept(Visitor visitor);
}