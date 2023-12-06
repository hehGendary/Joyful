
public class Operators {
    public final String operators = "+-*/()<>=";

    public boolean inParens(char character) {
        return "()[]{}".indexOf(character) != -1;
    }

    public boolean inOperators(char character) {
        return operators.contains(String.valueOf(character));
    }

    public String operatorType(String operator) {
        return switch (operator) {
            case "+", "-", "*", "/" -> operator;
            case "(" -> "LPAR";
            case ")" -> "RPAR";
            case "<" -> "LESS";
            case ">" -> "BIGGER";
            case "=" -> "MAKEEQUALS";
            case "==" -> "EQUALS";

            default -> "";
        };
    }
}