package infraestructure.ftp;

import domain.exceptions.FTPAuthenticationException;
import domain.exceptions.FTPConnectionException;
import domain.exceptions.FTPDirectoryOperationsException;
import domain.exceptions.FTPFileOperationsException;
import domain.ftp.FTPClient;
import obj.RemoteFile;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;

public class ApacheFTPClient implements FTPClient {
    private final org.apache.commons.net.ftp.FTPClient apacheClient;
    private PrintCommandListener listener;

    public ApacheFTPClient() {
        this.apacheClient = new org.apache.commons.net.ftp.FTPClient();
    }

    @Override
    public void login(String username, String password) throws FTPConnectionException {
        try {
            if (isConnected()) {
                boolean success = apacheClient.login(username, password);
                if (!success) {
                    throw new FTPAuthenticationException("Usuario o contraseña incorrectos");
                }
            }
        } catch (IOException e) {
            throw new FTPAuthenticationException("Error al hacer login", e);
        }

    }

    @Override
    public void setServerLogVisible() {
        this.listener = new PrintCommandListener(new PrintWriter(System.out), true);
        this.apacheClient.addProtocolCommandListener(listener);
    }

    @Override
    public void setServerLogInvisible() {
        if (this.listener != null) {
            this.apacheClient.removeProtocolCommandListener(listener);
            this.listener = null;
        }
    }

    @Override
    public void connect(String host, int port) throws FTPConnectionException {
        try {
            if (!isConnected()) {
                apacheClient.connect(host, port);
                int replyCode = apacheClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(replyCode)) {
                    disconnect();
                    throw new FTPConnectionException("El servidor FTP rechazó la conexión");
                }
            }
        } catch (IOException e) {
            throw new FTPConnectionException("Error de conexion al servidor", e);
        }
    }

    @Override
    public void disconnect() throws FTPConnectionException {
        try {
            if (isConnected()) {
                apacheClient.disconnect();
            }
        } catch (IOException e) {
            throw new FTPConnectionException("Error al desconectar del servidor", e);
        }
    }

    @Override
    public boolean isConnected() {
        return apacheClient.isConnected();
    }

    @Override
    public void changeDirectory(String path) throws FTPDirectoryOperationsException {
        try {
            boolean success = apacheClient.changeWorkingDirectory(path);
            if (!success) {
                throw new FTPDirectoryOperationsException("Error, la path no es valida");
            } else {
                System.out.println("Directorio cambiado de forma correcta");
            }
        } catch (IOException e) {
            throw new FTPDirectoryOperationsException("Error al cambiar de carpeta.", e);
        }
    }

    @Override
    public String getCurrentDirectory() throws FTPDirectoryOperationsException {
        try {
            return apacheClient.printWorkingDirectory();
        } catch (IOException e) {
            throw new FTPDirectoryOperationsException("Error al mostrar directorio", e);
        }
    }

    @Override
    public List<RemoteFile> getFileList(String path) throws FTPFileOperationsException {
        try {
            if (!isConnected()) {
                throw new FTPFileOperationsException("No conectado al servidor");
            }

            FTPFile[] apacheFiles = apacheClient.listFiles(path);
            if (apacheFiles == null) {
                return List.of();//lista vacia
            }
            List<RemoteFile> remoteList = new ArrayList<RemoteFile>();
            for (FTPFile f : apacheFiles) {
                RemoteFile miArchivo = new RemoteFile(f.getName(), f.getSize(), f.isDirectory());
                remoteList.add(miArchivo);
            }
            return remoteList;

        } catch (IOException e) {
            throw new FTPFileOperationsException("Error al listar archivos con metadatos", e);
        }
    }


    @Override
    public void upload(String localFile, String remotePath) throws FTPFileOperationsException {
        Path file = Path.of(localFile);
        try (InputStream inputStream = newInputStream(file)) {
            apacheClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

            boolean success = apacheClient.storeFile(remotePath, inputStream);

            if (!success) {
                throw new FTPFileOperationsException("No se pudo subir el archivo al servidor."+apacheClient.getReplyString());
            } else {
                System.out.println("Archivo subido correctamente");
            }


        } catch (IOException e) {
            throw new FTPFileOperationsException("Error al subir el archivo: "+apacheClient.getReplyString()+" "+ localFile, e);
        }
    }

    @Override
    public void download(String localFile, String remotePath) throws FTPFileOperationsException {
        Path file = Path.of(localFile);
        try (OutputStream outputStream = newOutputStream(file)) {
            apacheClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

            boolean success = apacheClient.retrieveFile(remotePath, outputStream);
            if (!success) {
                throw new FTPFileOperationsException("No se pudo descargar el archivo del servidor. "+apacheClient.getReplyString());
            } else {
                System.out.println("Archivo descargado correctamente");
            }
        } catch (IOException e) {
            throw new FTPFileOperationsException("Error al descargar el archivo: " +apacheClient.getReplyString()+" "+ remotePath, e);
        }
    }

    @Override
    public void delete(String remotePath) throws FTPFileOperationsException {
        try {
            boolean success = apacheClient.deleteFile(remotePath);
            if (!success) {
                throw new FTPFileOperationsException("No se pudo eliminar el archivo. Verifique que la ruta sea correcta y no sea un directorio.");
            } else {
                System.out.println("Archivo borrado correctamente");
            }
        } catch (IOException e) {
            throw new FTPFileOperationsException("Error al intentar eliminar el archivo: " + remotePath, e);
        }
    }

    @Override
    public void setPassiveModeTrue() {
        apacheClient.enterLocalPassiveMode();
    }

    @Override
    public void setPassiveModeFalse() {
        apacheClient.enterLocalActiveMode();
    }

    @Override
    public void createDirectory(String path, String name) throws FTPDirectoryOperationsException {
        try {
            String finalPath = path + "/" + name;
            boolean success = apacheClient.makeDirectory(finalPath);
            if (!success) {
                throw new FTPDirectoryOperationsException("No se pudo crear el directorio. Código de respuesta: " + apacheClient.getReplyCode());
            } else {
                System.out.println("Directorio creado correctamente.");
            }
        } catch (IOException e) {
            throw new FTPDirectoryOperationsException("Error de red al intentar crear el directorio", e);
        }
    }

    @Override
    public void renameDirectory(String path, String newName) throws FTPDirectoryOperationsException {
        try {
            boolean success = apacheClient.rename(path, newName);
            if (!success) {
                throw new FTPDirectoryOperationsException("Error al renombrar. Asegúrese de que el origen existe y el destino no está ocupado.");
            }
        } catch (IOException e) {
            throw new FTPDirectoryOperationsException("Error de red al intentar renombrar", e);
        }
    }
}

