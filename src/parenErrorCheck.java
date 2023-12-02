public class parenErrorCheck {
    String stringForCheck;
    String openParens = "([{";
    String closeParens = "}])";

    public parenErrorCheck(String stringForCheck) {
        this.stringForCheck = stringForCheck;
    }

    private boolean inOpenParens(char current) {
        return openParens.indexOf(current) != -1;
    }

    private boolean inCloseParens(char current) {
        return closeParens.indexOf(current) != -1;
    }

    public boolean check() {
        int parensNumber = 0;
        boolean inString = false;

        for (int pos = 0; pos < stringForCheck.length(); pos++) {
            char current = stringForCheck.charAt(pos);

            if (current == '"') {
                inString = !inString;
            }

            if (inOpenParens(current) && !inString) {
                parensNumber++;

            } else if (inCloseParens(current) && !inString) {
                parensNumber = parensNumber - 1;

                if (parensNumber < 0) {
                    return false;
                }
            }
        }

        return parensNumber != 0;
    }
}