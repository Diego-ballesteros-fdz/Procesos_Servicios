package domain.exceptions;

public class FTPFileOperationsException extends FTPException {

    public FTPFileOperationsException(String message,Throwable cause){
        super(message,cause);
    }
    public FTPFileOperationsException(String message){
        super(message);
    }
}
