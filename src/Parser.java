import AST.*;
import AST.Expressions.*;
import AST.Library.Funcs.Args.Arguments;
import AST.Statements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Parser {

    private static final Token EOF = new Token("EOF");

    private final List<Token> tokens;
    private final int size;

    private int pos;

    private boolean debug;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        debug = false;
        size = tokens.size();
    }

    public Parser(List<Token> tokens, boolean d) {
        this.tokens = tokens;
        debug = d;
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
                    String.format("Unknown token! Line %d, Char %d", get(0).line, get(0).ch));
            Expression printExpr = getExpr();
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
        if (match("USE")) {
            return use();
        }
        if (match("FUNC")) {
            return functionDefine();
        }
        if (match("RETURN")) {
            return new ReturnStatement(getExpr());
        }
        if (match("CLASS")) {
            return classDeclaration();
        }
        if (Objects.equals(get(0).type, "WORD") && Objects.equals(get(1).type, "LPAR")) {
            return new FunctionStatement(function());
        }
        return makeStatement();
    }

    private Statement use() {
        if (match("CANVAS")) return new UseStatement("canvas");
        throw new JFExpection("", "");

    }

    private Statement classDeclaration() {
        final String name = get(0).text;
        consume("WORD");
        final ClassDeclarationStatement classDeclaration = new ClassDeclarationStatement(name);
        consume("LBRACE");
        do {
            if (match("FUNC")) {
                classDeclaration.addMethod(functionDefine());
            } else {
                final Statement fieldDeclaration = makeStatement();
                if (fieldDeclaration != null) {
                    classDeclaration.addField((makeVariableStatement) fieldDeclaration);
                } else {
                    throw new JFExpection("", "Class can contain only assignments and function declarations");
                }
            }
        } while (!match("RBRACE"));
        return classDeclaration;
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
            final Expression index = getExpr();
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
        final Statement body = blOrSt();
        return new FunctionDefineStatement(name, argNames, body);
    }

    private Arguments arguments() {
        // (arg1, arg2, arg3 = expr1, arg4 = expr2)
        final Arguments arguments = new Arguments();
        boolean startsOptionalArgs = false;
        consume("LPAR");
        while (!match("RPAR")) {
            final String name = get(0).text;
            consume("WORD");
            if (match("MAKEEQUALS")) {
                startsOptionalArgs = true;
                arguments.addOptional(name, value());
            } else if (!startsOptionalArgs) {
                arguments.addRequired(name);
            } else {
                throw new JFExpection("", "Required argument cannot be after optional");
            }
            if (!match("COMMA")) {
                consume("RPAR");
                break;
            }
        }
        return arguments;
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

    private Expression array() {
        consume("OPENTREE");
        final List<Expression> elements = new ArrayList<>();
        while (!match("CLOSETREE")) {
            try {elements.add(getExpr());} catch (JFExpection J) {
                if (!match("COMMA")) {
                    consume("CLOSEDTREE");
                    break;
                }
            }
        }
        return new ArrayExpression(elements);
    }

    private Expression element() {
        final String variable = get(0).text;
        consume("WORD");
        consume("LBRACKET");
        final Expression index = getExpr();
        consume("RBRACKET");
        return new ArrayAccessExpression(variable, index);
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
        if (Objects.equals(get(0).type, "LBRACE")) return block();
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

    private Expression plusMinusExpr() {
        Expression result = mulDivExpr();

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

    private Expression mulDivExpr() {
        Expression result = unary();

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

    private Expression unary() {
        if (match("-")) {
            return new UnaryExpression('-', value());
        }
        if (match("!")) {
            return new UnaryExpression('!', value());
        }
        return value();
    }

    private Expression value() {
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
            Expression result = getExpr();
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
            if (debug) System.out.println(
                    get(0).toString() + get(1).toString() + get(2).toString()
            );
            throw new JFExpection("", get(0).toString() + get(1).toString() + get(2).toString());
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