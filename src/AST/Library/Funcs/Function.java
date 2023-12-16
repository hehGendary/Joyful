package AST.Library.Funcs;

import AST.Values.AbstractValue;

public interface Function {

    AbstractValue execute(AbstractValue... args);
}