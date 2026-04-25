import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;

public class SecureServer1 {
    public static void main(String[] args) {
        Scanner teclado=new Scanner(System.in);
		Boolean exist;
		String fileName;
        try {
			do{
				System.out.println("Indique el nombre de la clave que desea usar (Sin extensión)");
				
				String name = teclado.nextLine();
				
				fileName = name+ ".txt";
				
				Path filePath = Paths.get(fileName);
				
				exist = Files.exists(filePath);
				
				if(!exist) System.out.println("Error, el archivo no existe.");
            
			}while(!exist); 

            String base64Key = Files.readString(Paths.get(fileName));
            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");


            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            System.out.println("Servidor iniciado. Esperando conexión en el puerto 5000...");


            try (ServerSocket serverSocket = new ServerSocket(5000);
                 Socket clientSocket = serverSocket.accept();
                 DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
                
                System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

                int length = in.readInt();
                byte[] encryptedMessage = new byte[length];
                in.readFully(encryptedMessage); 

                byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
                
                System.out.println("Mensaje descifrado: " + new String(decryptedMessage));

            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
