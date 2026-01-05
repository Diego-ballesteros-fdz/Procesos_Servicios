

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPservidorInfinito {
     public static void main(String[] args) {
             int puerto=55555;
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

                    //leer el mensaje del cliente
                    String parametro = entrada.readUTF();

                    String numeros = entrada.readUTF();

                    //dividimos el string
                    String num[] =numeros.split("/");

                    int num1=Integer.valueOf(num[0]),num2=Integer.valueOf(num[1]);


                    int total = 0;
                    String mensaje="";
                    boolean error=false;
                    // en funcion de su selec hacemos cosas
                    switch (parametro) {
                        case "SUMA":
                            total = num1 + num2;
                            break;
                        case "RESTA":
                            total = num1 - num2;
                            break;
                        case "MULTI":
                            total = num1 * num2;
                            break;
                        default:
                            mensaje="Algo fallo al pasar los parametros.";
                            error=true;
                            break;

                    }

                    if(!error){
                        //formamos el mensaje
                        mensaje="El resultado de la "+parametro+" es "+String.valueOf(total);
                        //lo enviamos
                        salida.writeUTF(mensaje);
                        salida.flush();
                    }else{
                        salida.writeUTF(mensaje);
                    }
                    

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
