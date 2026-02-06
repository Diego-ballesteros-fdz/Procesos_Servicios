package domain.ftp;

import domain.exceptions.FTPFileOperationsException;
import obj.RemoteFile;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FTPFileOperations {

    List<RemoteFile> getFileList(String path);

    void upload(String localFile, String remotePath) throws FTPFileOperationsException;

    void download(String localFile, String remotePath) throws FTPFileOperationsException;

    void delete(String remotePath) throws FTPFileOperationsException;
}

