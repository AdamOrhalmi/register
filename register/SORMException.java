package register;

public class SORMException extends RuntimeException {
    public SORMException(String message) {
        super(message);
    }

    public SORMException(String message, Throwable cause) {
        super(message, cause);
    }
}
