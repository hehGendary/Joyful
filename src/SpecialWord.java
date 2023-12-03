public class SpecialWord {

    boolean active;

    public SpecialWord() {
        active = true;
    }
    public boolean isSpecial(String Text) {
        return switch (Text) {
            case "var", "num", "str", "print" -> true;
            default -> false;
        };
    }

    public String getTT (String Text) {
        return isSpecial(Text) ? Text : "WORD";
    }
}