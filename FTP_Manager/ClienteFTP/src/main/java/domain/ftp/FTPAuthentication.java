package domain.ftp;

import domain.exceptions.FTPAuthenticationException;

import java.io.IOException;

public interface FTPAuthentication {
    void login(String username, String password) throws FTPAuthenticationException;
}

