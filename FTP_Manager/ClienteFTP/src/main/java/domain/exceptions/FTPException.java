package domain.exceptions;

public class FTPException extends RuntimeException{

    protected FTPException(String message) {
        super(message);
    }

    protected FTPException(String message, Throwable cause) {
        super(message, cause);
    }
}
