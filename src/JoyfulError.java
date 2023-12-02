public class JoyfulError {
    boolean haveError = false;
    String errorDetails = "";

    public void addError(String errorDetails) {
        haveError = true;
        this.errorDetails = errorDetails;
    }
}//