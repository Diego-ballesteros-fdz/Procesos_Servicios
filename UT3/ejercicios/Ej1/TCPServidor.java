import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServidor {
    public static void main(String[] args) {
        int puerto=55555;
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado. Escuchando en el puerto " + puerto + "...");

            //atendemos la conexion del cliente1
            Socket socketCliente1 = servidor.accept();
            //entrada y salida
            DataInputStream entrada = new DataInputStream(socketCliente1.getInputStream());
            DataOutputStream salida = new DataOutputStream(socketCliente1.getOutputStream());
            //leemos del cliente 1
            String mensaje=entrada.readUTF();
            System.out.println("Leido del cliente 1, "+mensaje);
            //conexion con el cliente2
            Socket socketCliente2 = servidor.accept();
            //entrada y salida
            DataInputStream entrada2 = new DataInputStream(socketCliente2.getInputStream());
            DataOutputStream salida2 = new DataOutputStream(socketCliente2.getOutputStream());
            //se lo pasamos al cliente2
            salida2.writeUTF(mensaje);
            //recibimos el mensaje del cliente2
            mensaje= entrada2.readUTF();
            System.out.println("Recibido del Cliente2, "+mensaje);
            //se lo enviamos al cliente1
            salida.writeUTF(mensaje);
            //cerramos el server
            socketCliente1.close();
            socketCliente2.close();
            entrada.close();
            entrada2.close();
            salida.close();
            salida2.close();

        }catch (IOException eServidor) {
                    System.out.println("Error al crear el servidor: " + eServidor.getMessage());
                    eServidor.printStackTrace();
        }
    }
    
}
