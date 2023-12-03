import java.util.*;

public class Lexer {

    String code;
    List<Token> tokens;
    int codeLength;
    JoyfulError jfError;

    Operators operatorsClass = new Operators();
    String operators = operatorsClass.operators;
    public Lexer(String code, JoyfulError jfError) {
        this.code = code;
        this.jfError = jfError;
        this.codeLength = code.length();
        this.tokens = new ArrayList<>();
    }

    int pos;
    public LexerBox makeTokens() {
        for (int pos = 0; pos < codeLength; pos++) {
            if (jfError.haveError) {
                System.out.println(jfError.errorDetails);
                break;
            }
            char current = currentChar();

            if (Character.isDigit(current)) {
                makeNumber();
            }

            if (operatorsClass.inOperators(current)) {
                makeOperator();
            }

            if (Character.isLetter(current)) {
                makeWord();
            }

            if (current == ' ') pos++;
        }

        return new LexerBox(tokens, jfError);
    }

    private void makeWord() {
        StringBuilder word = new StringBuilder();
        char current = currentChar();

        while (Character.isLetterOrDigit(current)) {
            word.append(current);
            pos++;
            current = currentChar();
        }

        String Ttext = word.toString();
        String TType= new SpecialWord().getTT(Ttext);
        addToken(Ttext, TType);
    }

    private void makeNumber() {
        StringBuilder number = new StringBuilder();
        int dotCount = 0;
        char current = currentChar();

        while (Character.isDigit(current) || current == '.') {
            if (current == '.') dotCount++;
            number.append(current);
            pos++;
            current = currentChar();
        }

        if (dotCount > 1) {
            jfError.addError("Invalid float error! (" + number.toString() + ")");
        }
        addToken(number.toString(), "NUMBER");
    }

    private void makeOperator() {
        char current = currentChar();
        boolean startingInParens = operatorsClass.inParens(current);

        StringBuilder operator = new StringBuilder();

        while (operatorsClass.inOperators(current)) {
            if (operatorsClass.inParens(current) != startingInParens) {
                break;
            }
            if (startingInParens && operator.toString().length() == 1) {
                break;
            }

            operator.append(current);
            pos++;
            current = currentChar();
        }

        String operatorType = operatorsClass.operatorType(operator.toString());
        if (operatorType == "") {
            jfError.addError(operator.toString() + " not operator!");
        }
        addToken(operatorType);
    }

    private void tokenizeText() {
        next();
        final StringBuilder buffer = new StringBuilder();
        char current = currentChar();
        while (true) {
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
            if (current == '"') break;
            buffer.append(current);
            current = next();
        }
        next();

        addToken("STRING", buffer.toString());
    }

    private char currentChar() {
        try {
            return code.charAt(pos);
        } catch (Exception ex) {
            return '\0';
        }
    }

    private char next() {
        try {
            pos++;
            return code.charAt(pos);
        } catch (Exception ex) {
            return '\0';
        }
    }
    private void addToken(String type, String text) {
        tokens.add(new Token(text, type));
    }

    private void addToken(String type) {
        tokens.add(new Token(type));
    }
}