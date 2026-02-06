package domain.ftp;

import domain.exceptions.FTPDirectoryOperationsException;
import org.apache.commons.net.ftp.FTPFile;

public interface FTPDirectoryOperations {
    void changeDirectory(String path) throws FTPDirectoryOperationsException;
    String getCurrentDirectory();
    void createDirectory(String path,String name);
    void renameDirectory(String path,String name);
}
