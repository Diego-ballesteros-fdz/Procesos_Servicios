import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;

public class KeyGeneratorAES1 {
    public static void main(String[] args) {
		Scanner teclado=new Scanner(System.in);
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            
            keyGen.init(128);
            
            SecretKey secretKey = keyGen.generateKey();

            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            
            System.out.println("Indique el nombre del archivo donde se almacenará la clave (Sin extensión)");
            
            String name = teclado.nextLine();
            
            String fileName = name+ ".txt";
            
            Files.writeString(Paths.get(fileName), encodedKey);
            
            System.out.println("Clave AES de 128 bits generada y guardada en "+fileName);
            
        } catch (Exception e) {
            System.err.println("Error al generar la clave: " + e.getMessage());
        }
    }
}
