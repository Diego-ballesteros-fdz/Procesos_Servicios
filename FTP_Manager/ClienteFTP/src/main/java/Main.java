import controller.FTPController;
import domain.ftp.FTPClient;
import domain.ftp.FTPConnection;
import infraestructure.ftp.ApacheFTPClient;

public class Main {
    public static void main(String[] args) {
        ApacheFTPClient ftpClient = new ApacheFTPClient();
        FTPController controller = new FTPController(ftpClient);
        controller.start();
    }
}

