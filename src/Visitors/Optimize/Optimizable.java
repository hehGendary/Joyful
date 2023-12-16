package Visitors.Optimize;

import AST.Library.Nodes.Node;

public interface Optimizable {

    Node optimize(Node node);

    int optimizationsCount();

    String summaryInfo();
}