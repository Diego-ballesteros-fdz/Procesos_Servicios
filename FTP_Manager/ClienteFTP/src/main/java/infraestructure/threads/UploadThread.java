package infraestructure.threads;

import domain.exceptions.FTPConnectionException;
import domain.exceptions.FTPFileOperationsException;
import domain.ftp.FTPClient;
import infraestructure.ftp.ApacheFTPClient;

public class UploadThread implements Runnable {
    private FTPClient ftpClient;
    private String remotePath;
    private String localPath;
    private String host, user, pss;

    public UploadThread(String remotePath, String localPath, String host, String user, String pss) {
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
            ftpClient.upload(localPath , remotePath);
        } catch (FTPConnectionException e) {
            throw new FTPConnectionException("Error en el Thread de subida:", e);
        } catch (FTPFileOperationsException ex) {
            throw new FTPFileOperationsException("Error al subir el archivo en el Thread de subida", ex);
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (ftpClient != null) {
                ftpClient.disconnect();
                System.out.println("Hilo subida: Conexión cerrada.");
            }
        } catch (Exception e) {
            System.err.println("Hilo subida: No se pudo cerrar la conexión limpiamente.");
        }
    }
}
