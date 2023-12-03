import AST.Expression;
import AST.Statement;

import java.util.List;

public class ParserBox {
    public Statement ast;
    public JoyfulError jfError;

    public ParserBox(Statement ast, JoyfulError jfError) {
        this.ast = ast;
        this.jfError = jfError;
    }
}