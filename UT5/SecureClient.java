import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class SecureClient {
    public static void main(String[] args) {
        try {
            String base64Key = Files.readString(Paths.get("clave_aes.txt"));
            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            String mensajeSecreto = "Hola Mundo estoy cifrado!!!";
            
            byte[] encryptedMessage = cipher.doFinal(mensajeSecreto.getBytes());

            System.out.println("Conectando al servidor...");

            try (Socket socket = new Socket("192.168.1.42", 5000);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                
                out.writeInt(encryptedMessage.length);
                out.write(encryptedMessage);
                
                System.out.println("Mensaje cifrado enviado con éxito.");
            }

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
