package AST.Expressions;

import AST.Values.Value;

public interface Expression {
    String eval();

    Value valEval();
}