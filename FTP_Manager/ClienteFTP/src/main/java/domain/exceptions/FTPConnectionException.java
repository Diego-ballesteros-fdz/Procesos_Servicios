package domain.exceptions;



public class FTPConnectionException extends FTPException{

    public FTPConnectionException(String message, Throwable cause){
        super(message,cause);
    }
    public FTPConnectionException(String message){
        super(message);
    }
}
