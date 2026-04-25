import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class KeyGeneratorAES {
    public static void main(String[] args) {
        try {
            // 1. Instanciamos el generador de claves para el algoritmo AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            
            // 2. Definimos el tamaño de la clave (128 bits como pide el enunciado)
            keyGen.init(128);
            
            // 3. Generamos la clave secreta
            SecretKey secretKey = keyGen.generateKey();
            
            // 4. Extraemos los bytes de la clave y los codificamos en Base64
            // (Hacemos esto porque si guardamos los bytes en crudo, el archivo sería ilegible)
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            
            // 5. Guardamos la clave en un fichero usando java.nio de forma eficiente
            Files.writeString(Paths.get("clave_aes.txt"), encodedKey);
            
            System.out.println("Clave AES de 128 bits generada y guardada en 'clave_aes.txt'");
            
        } catch (Exception e) {
            System.err.println("Error al generar la clave: " + e.getMessage());
        }
    }
}
