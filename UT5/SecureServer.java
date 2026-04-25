import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class SecureServer {
    public static void main(String[] args) {
        try {
            // Leer y reconstruir la clave AES desde el fichero
            String base64Key = Files.readString(Paths.get("clave_aes.txt"));
            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

            // Configurar el objeto Cipher en modo DESCIFRADO
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            System.out.println("Servidor iniciado. Esperando conexión en el puerto 5000...");

            // Iniciar el Socket Servidor
            try (ServerSocket serverSocket = new ServerSocket(5000);
                 Socket clientSocket = serverSocket.accept();
                 DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
                
                System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

                // Leer la longitud del mensaje cifrado y luego los bytes
                int length = in.readInt();
                byte[] encryptedMessage = new byte[length];
                in.readFully(encryptedMessage); // Leemos exactamente la cantidad de bytes enviada

                // Descifrar el mensaje
                byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
                
                // Mostrar el resultado
                System.out.println("Mensaje cifrado (bytes): " + new String(encryptedMessage)); // Se verá con caracteres raros
                System.out.println("Mensaje descifrado: " + new String(decryptedMessage));

            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
