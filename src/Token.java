public class Token {
    public String text;
    public String type;
    boolean haveText;

    public Token(String type, String text) {
        this.type = type;
        this.text = text;
        this.haveText = true;
    }

    public Token(String type) {
        this.type = type;
        this.text = "";
        this.haveText = false;
    }


    public String toString() {
        if (haveText) return type + "(" + text + ")";
        return type;
    }
}