package domain.exceptions;

public class FTPAuthenticationException extends FTPException{

    public FTPAuthenticationException(String message,Throwable cuase){
        super(message,cuase);
    }
    public FTPAuthenticationException(String message){
        super(message);
    }

}
