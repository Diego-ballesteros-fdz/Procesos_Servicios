package domain.exceptions;

public class FTPDirectoryOperationsException extends FTPException{

    public FTPDirectoryOperationsException(String message,Throwable cause){
        super(message,cause);
    }
    public FTPDirectoryOperationsException(String message){
        super(message);
    }
}
