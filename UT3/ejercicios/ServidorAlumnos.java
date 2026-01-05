import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAlumnos {

    public static void main(String[] args) {
        int puerto = 55555;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado. Escuchando en el puerto " + puerto + "...");

            // Bucle infinito: el servidor atiende un cliente tras otro
            while (true) {
                System.out.println("Esperando conexión de un cliente...");
                
                // Aceptar conexión de un cliente (bloqueante)
                try (
                    Socket socketCliente = servidor.accept();
                    DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
                    DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream())
                ) {
                    System.out.println("Cliente conectado desde IP: " 
                            + socketCliente.getInetAddress().getHostAddress()
                            + " Puerto: " + socketCliente.getPort());

                    // 1) Recibir nombre del alumno
                    String nombreAlumno = entrada.readUTF();

                    // 2) Recibir IP enviada por el cliente
                    String ipAlumno = entrada.readUTF();

                    // Mostrar por pantalla datos recibidos
                    System.out.println("=== Datos del alumno conectado ===");
                    System.out.println("Alumno recibido: " + nombreAlumno);
                    System.out.println("IP alumno: " + ipAlumno);
                    System.out.println("IP socket cliente: " + socketCliente.getInetAddress().getHostAddress());
                    System.out.println("IP socket servidor: " + socketCliente.getPort());
                    System.out.println("==================================");

                    // 3) Enviar saludo personalizado
                    String saludo = "Hola alumno " + nombreAlumno;
                    salida.writeUTF(saludo);
                    salida.flush();

                    System.out.println("Cliente finalizado, esperando el siguiente...");

                } catch (IOException eCliente) {
                    System.out.println("Error atendiendo a un cliente: " + eCliente.getMessage());
                    eCliente.printStackTrace();
                    // Se continúa el bucle para seguir aceptando otros clientes
                }
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}