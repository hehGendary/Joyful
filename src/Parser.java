import AST.*;
import AST.Expressions.*;
import AST.Statements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Parser {

    private static final Token EOF = new Token("EOF");

    private final List<Token> tokens;
    private final int size;

    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public AbstractStatement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match("EOF")) {
            result.add(getStatement());
        }
        return result;
    }

    private AbstractStatement getStatement() {
        if (match("PRINT")) {
            if (!match("LPAR")) throw new
                    JFExpection("Syntax",
                    String.format("Unknown token! Line %d, Char %d", get(0).line, get(0).ch));
            AbstractExpression printExpr = getExpr();
            if (!match("RPAR")) throw new
                    JFExpection("Syntax",
                    String.format("Unknown token! Line %d, Char %d", get(0).line, get(0).ch));
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
        if (match("FUNC")) {
            return functionDefine();
        }
        if (match("RETURN")) {
            return new ReturnStatement(getExpr());
        }
        if (Objects.equals(get(0).type, "WORD") && Objects.equals(get(1).type, "LPAR")) {
            return new FunctionStatement(function());
        }
        return makeStatement();
    }

    private AbstractStatement whileSt() {
        final AbstractExpression condition = getExpr();
        final AbstractStatement statement = blOrSt();
        return new WhileStatement(condition, statement);
    }

    private AbstractStatement forSt() {
        consume("LPAR");
        AbstractStatement init = makeStatement();
        consume("COMMA");
        AbstractExpression term = getExpr();
        consume("COMMA");
        AbstractStatement incr = makeStatement();
        consume("RPAR");
        AbstractStatement st = blOrSt();
        return new ForStatement(init, term, incr, st);
    }

    private AbstractStatement makeStatement() {
        Token current = get(0);
        if (Objects.equals(get(0).type, "WORD") && Objects.equals(get(1).type, "MAKEEQUALS")) {
            final String variable = current.text;
            consume("WORD");
            consume("MAKEEQUALS");
            return new makeVariableStatement(variable, getExpr());
        }
        if (Objects.equals(get(0).type, "WORD") && Objects.equals(get(1).type, "LBRACKET")) {
            final String variable = get(0).text;
            consume("WORD");
            consume("LBRACKET");
            final AbstractExpression index = getExpr();
            consume("RBRACKET");
            consume("MAKEEQUALS");
            return new ArrayAssignmentStatement(variable, index, getExpr());
        }
        consume("WORD");
        throw new JFExpection("Unknown statement",
                "line: , char: ");
    }

    private FunctionDefineStatement functionDefine() {
        final String name = get(0).text;
        consume("WORD");
        consume("LPAR");
        final List<String> argNames;
        argNames = new ArrayList<>();
        while (!match("RPAR")) {
            argNames.add(get(0).text);
            match(get(0).type);
            match("COMMA");
        }
        final AbstractStatement body = blOrSt();
        return new FunctionDefineStatement(name, argNames, body);
    }

    private FunctionalExpression function() {
        final String name = get(0).text;
        consume("WORD");
        consume("LPAR");
        final FunctionalExpression function = new FunctionalExpression(name);
        while (!match("RPAR")) {
            function.addArgument(getExpr());
            if (!match("COMMA")) {
                consume("RPAR");
                break;
            }
        }
        return function;
    }

    private AbstractExpression array() {
        consume("OPENTREE");
        final List<AbstractExpression> elements = new ArrayList<>();
        while (!match("CLOSETREE")) {
            elements.add(getExpr());
            if (!match("COMMA")) {
                consume("CLOSEDTREE");
                break;
            }
        }
        return new ArrayExpression(elements);
    }

    private AbstractExpression element() {
        final String variable = get(0).text;
        consume("WORD");
        consume("LBRACKET");
        final AbstractExpression index = getExpr();
        consume("RBRACKET");
        return new ArrayAccessExpression(variable, index);
    }
    private AbstractStatement block() {
        BlockStatement st = new BlockStatement();
        consume("LBRACE");
        while (!match("RBRACE")) {
            st.add(getStatement());
        }
        return st;
    }

    private AbstractStatement blOrSt() {
        if (Objects.equals(get(0).type, "LBRACE")) return block();
        return getStatement();
    }

    private AbstractStatement ifElse() {
        consume("LPAR");
        AbstractExpression cond = getExpr();
        consume("RPAR");
        AbstractStatement ifSt = blOrSt();
        AbstractStatement elseSt;
        if (match("ELSE")) {
            elseSt = blOrSt();
        } else {
            elseSt = null;
        }
        return new ifElseStatement(cond, ifSt, elseSt);
    }
    private AbstractExpression getExpr() {
        return OrExpr();
    }

    private AbstractExpression OrExpr() {
        AbstractExpression result = AndExpr();

        while (true) {
            if (match("|")) {
                result = new BinaryExpression('|', result, AndExpr());
            } else {
                break;
            }
        }

        return result;
    }

    private AbstractExpression AndExpr() {
        AbstractExpression result = condExpr();

        while (true) {
            if (match("&")) {
                result = new BinaryExpression('&', result, condExpr());
            } else {
                break;
            }
        }

        return result;
    }

    private AbstractExpression condExpr() {
        AbstractExpression result = plusMinusExpr();

        while (true) {
            if (match("==")) {
                result = new BinaryExpression('=', result, plusMinusExpr());
                continue;
            }
            if (match("%")) {
                result = new BinaryExpression('%', result, plusMinusExpr());
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

    private AbstractExpression plusMinusExpr() {
        AbstractExpression result = mulDivExpr();

        while (true) {
            String current = get(0).type;

            if (match("+") || match("-")) {
                result = new BinaryExpression(current.charAt(0), result, mulDivExpr());
            } else {
                break;
            }
        }

        return result;
    }

    private AbstractExpression mulDivExpr() {
        AbstractExpression result = unary();

        while (true) {
            String current = get(0).type;

            if (match("*") || match("/")) {
                result = new BinaryExpression(current.charAt(0), result, unary());
            } else {
                break;
            }
        }

        return result;
    }

    private AbstractExpression unary() {
        if (match("-")) {
            return new UnaryExpression('-', primary());
        }
        if (match("!")) {
            return new UnaryExpression('!', primary());
        }
        return primary();
    }

    private AbstractExpression primary() {
        final Token current = get(0);
        if (Objects.equals(get(0).type, "OPENTREE")) {
            return array();
        }
        if (Objects.equals(get(0).type, "WORD") && Objects.equals(get(1).type, "LBRACKET")) {
            return element();
        }
        if (match("NUMBER")) {
            return new ValueExpression(Double.parseDouble(current.text));
        }
        if (match("STRING")) {
            return new ValueExpression(current.text);
        }
        if (match("LPAR")) {
            AbstractExpression result = getExpr();
            match("RPAR");
            return result;
        }
        if (Objects.equals(get(0).type, "WORD") && Objects.equals(get(1).type, "LPAR")) {
            return function();
        }
        if (match("WORD"))  {
            return new VariableExpression(current.text);
        }
        consume("WORD");
        return new ValueExpression(777);
    }

    private void consume(String type) {

        if (!match(type)) {
            System.out.println(get(0).toString() + get(1).toString() + get(2).toString());
            throw new JFExpection("", "");
        }
    }
    private boolean match(String type) {
        final Token current = get(0);
        if (!Objects.equals(type, current.type)) return false;
        pos++;
        return true;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}