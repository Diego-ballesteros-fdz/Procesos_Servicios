package domain.ftp;

public interface FTPClient extends
        FTPAuthentication,
        FTPConnection,
        FTPDirectoryOperations,
        FTPFileOperations {
}
