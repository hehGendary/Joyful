package MainFiles;

public class Token {
    public String text;
    public String type;

    public int ch;
    public int line;
    boolean haveText;

    public Token(String type, String text, int ch, int line) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.ch = ch;
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