package controller;


import infraestructure.threads.DownloadThread;
import infraestructure.threads.UploadThread;
import obj.RemoteFile;
import domain.ftp.FTPClient;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FTPController {
    private final FTPClient ftpClient;
    private final ExecutorService threadPool;
    private final Scanner scanner;
    private String user,pass,host;
    private boolean isLogEnabled = false;
    private boolean isPasvEnabled = false;

    public FTPController(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
        this.scanner = new Scanner(System.in);
        this.threadPool = Executors.newFixedThreadPool(4);
    }

    public void start() {
            System.out.println("--- Cliente FTP Consola ---");
            System.out.print("Host: ");
            this.host = scanner.nextLine();
            System.out.print("Puerto (defecto 21): ");
            String portStr = scanner.nextLine();
            int port = 21; // Valor por defecto
            try {
                if (!portStr.isEmpty()) {
                    port = Integer.parseInt(portStr);
                }
                try {
                    if (!portStr.isEmpty()) port = Integer.parseInt(portStr);
                } catch (NumberFormatException e) {
                    System.out.println(" Puerto no válido. Usando puerto 21.");
                }

                ftpClient.connect(host, port);

                System.out.print("Usuario: ");
                this.user = scanner.nextLine();
                System.out.print("Password: ");
                this.pass = scanner.nextLine();

                ftpClient.login(user, pass);

                IOPassvMode();

                showMenu();

            } catch (Exception e) {
                System.err.println("Error crítico: " + e.getMessage());
            } finally {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    System.err.println("Error al desconectar.");
                }
            }
        }

        private void showMenu () {
            boolean exit = false;
            while (!exit) {
                //clearConsole();
                System.out.println("\n--- Menú FTP (Directorio actual: " + getSafeCurrentDir() + ") ---");
                System.out.println("1. Listar archivos");
                System.out.println("2. Cambiar directorio (cd)");
                System.out.println("3. Descargar archivo");
                System.out.println("4. Subir archivo");
                System.out.println("5. Eliminar archivo");
                System.out.println("6. Crear carpeta");
                System.out.println("7. Renombrar carpeta");
                System.out.println("8. Activar/Desactivar modo pasivo");
                System.out.println("9. Activar/Desactivar respuestas del servidor");
//                System.out.println("10. Subir carpeta");
//                System.out.println("11. Descargar carpeta");
                System.out.println("10. Salir");
                System.out.print("Seleccione una opción: ");

                String option = scanner.nextLine();
                try {
                    switch (option) {
                        case "1" : {
                            listFiles();
                            break;
                        }
                        case "2" : {
                            changeDir();
                            break;
                        }
                        case "3" : {
                            downloadFile();
                            break;
                            }
                        case "4" : {
                            uploadFile();
                            break;
                        }
                        case "5" : {
                            deleteFile();
                            break;
                        }
                        case "6" : {
                            createDirectory();
                            break;
                        }
                        case "7" :{
                            renameDirectory();
                            break;
                        }
                        case "8" :{
                            IOPassvMode();
                            break;
                        }
                        case "9" :{
                            IOLogsMode();
                            break;
                        }
                        //TODO
//                        case "10" ->; //subir dir;
//                        case "11" ->; //descargar dir;
                        case "10" :{
                            exit = true;
                            ftpClient.disconnect();
                            break;
                        }
                        default : {
                            System.out.println("Opción no válida.");
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error en la operación: " + e.getMessage());
                }
                if (!exit) {
                    System.out.println("Presione enter para continuar.");
                    scanner.nextLine();
                }
            }
        }

        // --- Métodos de acción ---

        private void createDirectory() throws Exception{
            System.out.println("Indique la carpeta remota donde desea crear la nueva carpeta: ");
            String path = scanner.nextLine();
            System.out.println("Indique el nombre de la carpeta: ");
            String name = scanner.nextLine();
            ftpClient.createDirectory(path,name);
        }

        private void IOPassvMode(){
            if(isPasvEnabled){
                System.out.println("Entrando a modo PORT");
                isPasvEnabled=false;
                ftpClient.setPassiveModeFalse();
            }else{
                System.out.println("Entrando a modo PASV");
                isPasvEnabled=true;
                ftpClient.setPassiveModeTrue();
            }
        }

        private void IOLogsMode(){
            if(isLogEnabled){
                System.out.println("Desactivando logs");
                isLogEnabled=false;
                ftpClient.setServerLogInvisible();
            }else{
                System.out.println("Activando logs");
                isLogEnabled=true;
                ftpClient.setServerLogVisible();
            }
        }

        private void renameDirectory()throws Exception{
            System.out.println("Indique la carpeta remota a la que desea cambiar el nombre(sin añadir / al final): ");
            String path = scanner.nextLine();
            System.out.println("Indique el nuevo nombre de la carpeta: ");
            String name = scanner.nextLine();
            ftpClient.renameDirectory(path,name);
        }

        private void listFiles () throws Exception {
            List<RemoteFile> files = ftpClient.getFileList("");
            System.out.println("\nContenido:");
            for (RemoteFile f : files) {
                String prefix;
                if (f.isDirectory()) {
                    prefix = "[DIR]";
                } else {
                    prefix = "[FILE]";
                }
                String fileName = f.getName();
                long fileSize = f.getSize();
                System.out.println(prefix + " " + fileName + " " + fileSize + " bytes");
            }
        }

        private String getSafeCurrentDir () {
            try {
                return ftpClient.getCurrentDirectory();
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        private void changeDir () throws Exception {
            System.out.println("Indique la carpeta remota a la que desea moverse: ");
            String path = scanner.nextLine();
            ftpClient.changeDirectory(path);
        }

        private void downloadFile () throws Exception {
            System.out.println("Indique la ruta remota del archivo que desea descargar: ");
            String remotePath = scanner.nextLine();
            System.out.println("Indique la ruta local donde desea guardar los archivos: ");
            String localPath = scanner.nextLine();
            threadPool.execute(new DownloadThread(remotePath,localPath,host,user,pass));
        }

        private void uploadFile () throws Exception {
            System.out.println("Indique la ruta local del archivo que desea subir: ");
            String localPath = scanner.nextLine();
            System.out.println("Indique la ruta remota donde desea guardar el archivo subido: ");
            String remotePath = scanner.nextLine();
            threadPool.execute(new UploadThread(remotePath,localPath,host,user,pass));
        }

        private void deleteFile () throws Exception {
            System.out.println("Indique la ruta remota del archivo que desea eliminar: ");
            String remotePath = scanner.nextLine();
            ftpClient.delete(remotePath);
        }

        public void clearConsole () {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
