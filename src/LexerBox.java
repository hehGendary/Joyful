import java.util.List;

public class LexerBox {
    public List<Token> tokens;
    public JoyfulError jfError;

    public LexerBox(List<Token> tokens, JoyfulError jfError) {
        this.tokens = tokens;
        this.jfError = jfError;
    }
}