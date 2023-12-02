import AST.Expression;

import java.util.List;

public class ParserBox {
    public List<Expression> ast;
    public JoyfulError jfError;

    public ParserBox(List<Expression> ast, JoyfulError jfError) {
        this.ast = ast;
        this.jfError = jfError;
    }
}