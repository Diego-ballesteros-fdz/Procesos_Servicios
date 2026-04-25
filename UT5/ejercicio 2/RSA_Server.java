import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSA_Server {
    public static void main(String[] args) {
        try {
            System.out.println("Generando llaves RSA...");
            
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            
            kpg.initialize(2048);
            
            KeyPair kp = kpg.generateKeyPair();
            
            PublicKey publicKey = kp.getPublic();
            
            PrivateKey privateKey = kp.getPrivate();

            System.out.println("Servidor esperando en el puerto 6000...");
            
            try (ServerSocket serverSocket = new ServerSocket(6000);
                 Socket socket = serverSocket.accept();
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                System.out.println("Cliente conectado.");


                byte[] pubKeyBytes = publicKey.getEncoded();
                out.writeInt(pubKeyBytes.length);
                out.write(pubKeyBytes);
                System.out.println("Clave pública enviada al cliente.");

                // Recibir el mensaje cifrado
                int length = in.readInt();
                byte[] encryptedMsg = new byte[length];
                in.readFully(encryptedMsg);
                System.out.println("Mensaje cifrado recibido.");

                // Descifrar con la clave privada
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] decryptedMsg = cipher.doFinal(encryptedMsg);

                System.out.println("Mensaje descifrado: " + new String(decryptedMsg));
            }

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
