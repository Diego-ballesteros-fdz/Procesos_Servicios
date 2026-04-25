import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

public class RSA_Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Conectando al servidor...");
            
            try (Socket socket = new Socket("192.168.1.42", 6000);
            
                 DataInputStream in = new DataInputStream(socket.getInputStream());
                 
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                //recibir la clave pública del servidor
                int pubKeyLength = in.readInt();
                
                byte[] pubKeyBytes = new byte[pubKeyLength];
                
                in.readFully(pubKeyBytes);

				// Formato estandar de claves privadas
                X509EncodedKeySpec spec = new X509EncodedKeySpec(pubKeyBytes);
                
                KeyFactory kf = KeyFactory.getInstance("RSA");
                
                PublicKey publicKey = kf.generatePublic(spec);
                
                System.out.println("Clave pública recibida y reconstruida.");

                System.out.print("Escribe el mensaje para el servidor: ");
                
                String mensaje = sc.nextLine();

                Cipher cipher = Cipher.getInstance("RSA");
                
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                
                byte[] encryptedMsg = cipher.doFinal(mensaje.getBytes());

                out.writeInt(encryptedMsg.length);
                
                out.write(encryptedMsg);
                
                System.out.println("Mensaje cifrado enviado.");
            }

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
