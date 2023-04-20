package password;

public class InvalidAlgorithm extends Exception {
    public InvalidAlgorithm(String errorMessage) {
        super(errorMessage);
    }
}
