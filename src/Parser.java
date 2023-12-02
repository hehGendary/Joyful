import AST.BinaryExpression;
import AST.Expression;
import AST.NumberExpression;
import AST.UnaryExpression;

import java.util.ArrayList;
import java.util.List;

public final class Parser {

    private static final Token EOF = new Token("EOF");

    private final List<Token> tokens;
    private final int size;
    public JoyfulError jfError;

    private int pos;

    public Parser(List<Token> tokens, JoyfulError jfError) {
        this.tokens = tokens;
        this.jfError = jfError;
        size = tokens.size();
    }

    public ParserBox parse() {
        final List<Expression> result = new ArrayList<>();
        while (!match("EOF") && !jfError.haveError) {
            result.add(expression());
        }
        return new ParserBox(result, jfError);
    }

    private Expression expression() {
        return plusMinusExpr();
    }

    private Expression plusMinusExpr() {
        Expression result = multiplicative();

        while (true) {
            String current = get(0).type;

            if (match("+") || match("-")) {
                result = new BinaryExpression(current.charAt(0), result, multiplicative());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression multiplicative() {
        Expression result = unary();

        while (true) {
            String current = get(0).type;

            if (match("*") || match("/")) {
                result = new BinaryExpression(current.charAt(0), result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression unary() {
        if (match("-")) {
            return new UnaryExpression('-', primary());
        }
        return primary();
    }

    private Expression primary() {
        final Token current = get(0);
        if (match("NUMBER")) {
            return new NumberExpression(Double.parseDouble(current.text));
        }
        if (match("LPAR")) {
            Expression result = expression();
            match("RPAR");
            return result;
        }
        jfError.addError("Unknown token!");
        return new NumberExpression(Double.parseDouble("0"));
    }

    private boolean match(String type) {
        final Token current = get(0);
        if (type != current.type) return false;
        pos++;
        return true;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}