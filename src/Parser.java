import AST.*;
import AST.Expressions.*;
import AST.Statements.*;

import java.util.List;

public final class Parser {

    private static final Token EOF = new Token("EOF");

    private final List<Token> tokens;
    private final int size;

    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public Statement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match("EOF")) {
            result.add(getStatement());
        }
        return result;
    }

    private Statement getStatement() {
        if (match("PRINT")) {
            if (!match("LPAR")) throw new
                    JFExpection("Syntax",
                    String.format("Unknown token! Line %i, Char %i", get(0).line, get(0).ch));
            Expression printExpr = getExpr();
            if (!match("RPAR")) throw new
                    JFExpection("Syntax",
                    String.format("Unknown token! Line %i, Char %i", get(0).line, get(0).ch));
            return new PrintStatement(printExpr);
        }
        if (match("IF")) {
            return ifElse();
        }
        if (match("FOR")) {
            return forSt();
        }
        if (match("WHILE")) {
            return whileSt();
        }
        return makeStatement();
    }

    private Statement whileSt() {
        final Expression condition = getExpr();
        final Statement statement = blOrSt();
        return new WhileStatement(condition, statement);
    }

    private Statement forSt() {
        consume("LPAR");
        Statement init = makeStatement();
        consume("COMMA");
        Expression term = getExpr();
        consume("COMMA");
        Statement incr = makeStatement();
        consume("RPAR");
        Statement st = blOrSt();
        return new ForStatement(init, term, incr, st);
    }

    private Statement makeStatement() {
        Token current = get(0);
        if (match("WORD") && get(0).type == "MAKEEQUALS") {
            final String variable = current.text;
            consume("MAKEEQUALS");
            return new makeVariableStatement(variable, getExpr());
        }
        throw new JFExpection("Unknown statement",
                String.format("line: %i, char: %i", current.line, current.ch));
    }

    private Statement block() {
        BlockStatement st = new BlockStatement();
        consume("LBRACE");
        while (!match("RBRACE")) {
            st.add(getStatement());
        }
        return st;
    }

    private Statement blOrSt() {
        if (get(0).type == "LBRACE") return block();
        return getStatement();
    }

    private Statement ifElse() {
        consume("LPAR");
        Expression cond = getExpr();
        consume("RPAR");
        Statement ifSt = blOrSt();
        Statement elseSt;
        if (match("ELSE")) {
            elseSt = blOrSt();
        } else {
            elseSt = null;
        }
        return new ifElseStatement(cond, ifSt, elseSt);
    }
    private Expression getExpr() {
        return OrExpr();
    }

    private Expression OrExpr() {
        Expression result = AndExpr();

        while (true) {
            if (match("|")) {
                result = new BinaryExpression('|', result, AndExpr());
                continue;
            } else {
                break;
            }
        }

        return result;
    }

    private Expression AndExpr() {
        Expression result = condExpr();

        while (true) {
            if (match("&")) {
                result = new BinaryExpression('&', result, condExpr());
                continue;
            } else {
                break;
            }
        }

        return result;
    }

    private Expression condExpr() {
        Expression result = plusMinusExpr();

        while (true) {
            if (match("==")) {
                result = new BinaryExpression('=', result, plusMinusExpr());
                continue;
            }
            if (match(">")) {
                result = new BinaryExpression('>', result, plusMinusExpr());
                continue;
            }
            if (match("<")) {
                result = new BinaryExpression('<', result, plusMinusExpr());
                continue;
            }
            break;
        }

        return result;
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
        if (match("!")) {
            return new UnaryExpression('!', primary());
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
            consume("RPAR");
            return result;
        }
        if (match("WORD"))  {
            return new VariableExpression(current.text);
        }
        throw new JFExpection("Unknown token", "");
    }

    private void consume(String type) {
        if (!match(type)) throw new JFExpection("", "");
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