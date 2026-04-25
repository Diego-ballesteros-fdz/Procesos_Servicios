package infraestructure.threads;

import domain.exceptions.FTPConnectionException;
import domain.exceptions.FTPFileOperationsException;
import domain.ftp.FTPClient;
import infraestructure.ftp.ApacheFTPClient;

public class DownloadThread implements Runnable {
    private FTPClient ftpClient;
    private String remotePath;
    private String localPath;
    private String host, user, pss;

    public DownloadThread(String remotePath, String localPath, String host, String user, String pss) {
        this.ftpClient = new ApacheFTPClient();
        this.remotePath = remotePath;
        this.localPath = localPath;
        this.host = host;
        this.user = user;
        this.pss = pss;
    }

    private void createConnection() throws FTPConnectionException {
        ftpClient.connect(host, 21);
        ftpClient.login(user, pss);
    }

    @Override
    public void run() {
        try {
            createConnection();
            ftpClient.setPassiveModeTrue();
            ftpClient.download(localPath, remotePath);
        } catch (FTPConnectionException e) {
            throw new FTPConnectionException("Error en el Thread de bajada: ", e);
        } catch (FTPFileOperationsException ex) {
            throw new FTPFileOperationsException("Error al bjaar el archivo en el Thread de bajada", ex);
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (ftpClient != null) {
                ftpClient.disconnect();
                System.out.println("Hilo de bajada: Conexión cerrada.");
            }
        } catch (Exception e) {
            System.err.println("Hilo de bajada: No se pudo cerrar la conexión limpiamente.");
        }
    }
}
