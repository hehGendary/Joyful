package MainFiles;

import MainFiles.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Lexer {

    private static final String OPERATOR_CHARS = "+-*/%()[]{}=<>!&|,^~?:%.";
    private static final Map<String, String> OPERATORS;
    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", "+");
        OPERATORS.put("-", "-");
        OPERATORS.put("*", "*");
        OPERATORS.put("/", "/");
        OPERATORS.put("%", "%");
        OPERATORS.put("(", "LPAR");
        OPERATORS.put(")", "RPAR");
        OPERATORS.put("=", "MAKEEQUALS");
        OPERATORS.put("==", "==");
        OPERATORS.put("!", "!");
        OPERATORS.put("&", "&");
        OPERATORS.put("|", "|");
        OPERATORS.put("{", "LBRACE");
        OPERATORS.put("}", "RBRACE");
        OPERATORS.put("]", "RBRACKET");
        OPERATORS.put("[", "LBRACKET");
        OPERATORS.put(",", "COMMA");
        OPERATORS.put(";", "SEMICOLON");
        OPERATORS.put(">", ">");
        OPERATORS.put("<<", "OPENTREE");
        OPERATORS.put(">>", "CLOSEDTREE");
        OPERATORS.put("<", "<");
        OPERATORS.put(".", "DOT");
    }

    private final String input;
    private final int length;

    private final List<Token> tokens;

    private int pos;

    private int line = 1;
    private int ch = 1;

    public Lexer(String input) {
        this.input = input;
        length = input.length();

        tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (pos < length) {
            final char current = peek(0);
            if (current == '\n') line++; ch = 0;
            if (Character.isDigit(current)) tokenizeNumber();
            else if (Character.isLetter(current)) tokenizeWord();
            else if (current == '"') tokenizeText();
            else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                next();
            }
            ch++;
        }
        return tokens;
    }

    private void tokenizeNumber() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '\'') {
                next();
                current = peek(0);
            }
            if (current == '.') {
                if (buffer.indexOf(".") != -1) throw new RuntimeException("bad float");
            } else if (!Character.isDigit(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        addToken("NUMBER", buffer.toString());
    }

    private void tokenizeOperator() {
        char current = peek(0);
        if (current == '/') {
            if (peek(1) == '/') {
                next();
                next();
                tokenizeComment();
                return;
            } else if (peek(1) == '*') {
                next();
                next();
                tokenizeMultilineComment();
                return;
            }
        }
        final StringBuilder buffer = new StringBuilder();
        while (true) {
            final String text = buffer.toString();
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                addToken(OPERATORS.get(text));
                return;
            }
            buffer.append(current);
            current = next();
        }
    }

    private void tokenizeWord() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '\n') break;
            if (!Character.isLetterOrDigit(current) && (current != '_')  && (current != '$')) {
                break;
            }
            buffer.append(current);
            current = next();
        }

        final String word = buffer.toString();
        switch (word) {
            case "if": addToken("IF"); break;
            case "else": addToken("ELSE"); break;
            case "for": addToken("FOR"); break;
            case "while": addToken("WHILE"); break;
            case "func": addToken("FUNC"); break;
            case "return": addToken("RETURN"); break;
            case "use": addToken("USE"); break;
            case "try": addToken("TRY"); break;
            case "mistake": addToken("MISTAKE"); break;

            case "canvas": addToken("CANVAS"); break;
            case "convert": addToken("CONVERT"); break;
            case "math": addToken("MATH"); break;
            case "run": addToken("RUN"); break;
            case "strings": addToken("STRINGS"); break;
            default:
                addToken("WORD", word);
                break;
        }
    }

    private void tokenizeText() {
        boolean isDouble = peek(0) == '"';
        next();// skip " or '
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '\0') throw new RuntimeException("lmao");
            if (current == '\\') {
                current = next();
                switch (current) {
                    case '"': current = next(); buffer.append('"'); continue;
                    case 'n': current = next(); buffer.append('\n'); continue;
                    case 't': current = next(); buffer.append('\t'); continue;
                }
                buffer.append('\\');
                continue;
            }
            if (current == '"' && isDouble) break;
            if (current == '\'' && (!isDouble)) break;
            buffer.append(current);
            current = next();
        }
        next(); // skip closing " or '

        addToken("STRING", buffer.toString());
    }

    private void tokenizeComment() {
        char current = peek(0);
        while ("\r\n\0".indexOf(current) == -1) {
            current = next();
        }
    }

    private void tokenizeMultilineComment() {
        char current = peek(0);
        while (true) {
            if (current == '\0') throw new RuntimeException("multierror");
            if (current == '*' && peek(1) == '/') break;
            current = next();
        }
        next(); // *
        next(); // /
    }

    private char next() {
        pos++;
        final char result = peek(0);
        return result;
    }

    private char peek(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= length) return '\0';
        return input.charAt(position);
    }

    private void addToken(String type) {
        tokens.add(new Token(type));
    }

    private void addToken(String type, String text) {
        tokens.add(new Token(type, text, ch, line));
    }


}