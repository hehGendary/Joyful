package MainFiles.AST;

public final class JFExpection extends RuntimeException {
    String type;
    String text;

    public JFExpection(String type, String text) {
        super();
        //System.out.println(type + text);
        this.type = type;
        this.text = "\u001b[31m" + text;
    }
}