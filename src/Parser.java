import AST.*;

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
        final BlockStatement result = new BlockStatement();
        while (!match("EOF") && !jfError.haveError) {
            result.add(getStatement());
        }
        return new ParserBox(result, jfError);
    }

    private Statement getStatement() {
        if (match("PRINT")) {
            if (!match("LPAR")) jfError.addError("Unknown token " + get(0).toString());
            Expression printExpr = getExpr();
            if (!match("RPAR")) jfError.addError("Unknown token" + get(0).toString());
            return new PrintStatement(printExpr);
        }
        return makeStatement();
    }

    private Statement makeStatement() {
        if (match("WORD")) {
            String varName = get(-1).text;
            if (!match("MAKEEQUALS")) jfError.addError("Unkniwn syntax!");
            return new makeVariableStatement(varName, getExpr());
        }
        jfError.addError("Unknown statement!++");
        return new BlockStatement();
    }

    private Expression getExpr() {
        return plusMinusExpr();
    }

    private Expression plusMinusExpr() {
        Expression result = mulDivExpr();

        while (true) {
            String current = get(0).type;

            if (match("+") || match("-")) {
                result = new BinaryExpression(current.charAt(0), result, mulDivExpr());
                continue;
            } else {
                break;
            }
        }

        return result;
    }

    private Expression mulDivExpr() {
        Expression result = unary();

        while (true) {
            String current = get(0).type;

            if (match("*") || match("/")) {
                result = new BinaryExpression(current.charAt(0), result, unary());
                continue;
            } else {
                break;
            }
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
            return new ValueExpression(Double.parseDouble(current.text));
        }
        if (match("STRING")) {
            return new ValueExpression(current.text);
        }
        if (match("LPAR")) {
            Expression result = getExpr();
            match("RPAR");
            return result;
        }
        if (match("WORD"))  {
            return new ValueExpression(
                    Variables.get(current.text).asDouble()
            );
        }
        jfError.addError("Unknown token!" + get(0).toString() + " " + pos);
        return new ValueExpression(Double.parseDouble("0"));
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