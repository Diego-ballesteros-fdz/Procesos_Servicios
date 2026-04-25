package domain.ftp;

import domain.exceptions.FTPConnectionException;

import java.io.IOException;

public interface FTPConnection {
    void connect(String host, int port) throws FTPConnectionException;

    void disconnect() throws FTPConnectionException, IOException;

    boolean isConnected();

    void setPassiveModeTrue();
    void setPassiveModeFalse();

    void setServerLogVisible();
    void setServerLogInvisible();
}

