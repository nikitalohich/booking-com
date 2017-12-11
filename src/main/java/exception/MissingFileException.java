package exception;

public class MissingFileException extends RuntimeException {
    public MissingFileException() {
        super();
    }

    public MissingFileException(String message) {
        super(message);
    }

    public MissingFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingFileException(Throwable cause) {
        super(cause);
    }

    @Override
    public void printStackTrace() {
        System.err.println("File can't be found!");
    }
}