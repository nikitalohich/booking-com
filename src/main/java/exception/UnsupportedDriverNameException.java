package exception;

public class UnsupportedDriverNameException extends RuntimeException{
    public UnsupportedDriverNameException() {
        super();
    }

    public UnsupportedDriverNameException(String message) {
        super(message);
    }

    public UnsupportedDriverNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedDriverNameException(Throwable cause) {
        super(cause);
    }

    protected UnsupportedDriverNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public void printStackTrace() {
        System.err.println("Check driver name in the input file!");
    }
}
