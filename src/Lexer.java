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
        }

        return new LexerBox(tokens, jfError);
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

    private char currentChar() {
        try {
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