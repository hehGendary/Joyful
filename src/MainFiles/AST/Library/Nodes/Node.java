package MainFiles.AST.Library.Nodes;

import MainFiles.Visitors.ResultVisitor;
import MainFiles.Visitors.Visitor;

public interface Node {
    void accept(Visitor visitor);

    <R, T> R accept(ResultVisitor<R, T> visitor, T input);
}