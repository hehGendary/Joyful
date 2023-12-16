package AST.Library.Nodes;

import Visitors.ResultVisitor;
import Visitors.Visitor;

public interface Node {
    void accept(Visitor visitor);

    <R, T> R accept(ResultVisitor<R, T> visitor, T input);
}