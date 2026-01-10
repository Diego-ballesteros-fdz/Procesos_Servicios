import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServidor {
    public static void main(String[] args) {
        int puerto=55555;
        //creamos el shocket
        try(ServerSocket servidor=new ServerSocket(puerto)){
            System.out.println("Servidor iniciado en el puerto: "+puerto);
            //Para que sea infinito debemos crear un bucle while(true)
            while(true){
                System.out.println("Esperando conexion de cliente...");

                //aceptar la conexion de un cliente, de manera bloqueante y obteniendo su entrad ay salida.
                try(
                    Socket socketCliente=servidor.accept();
                    DataInputStream entrada=new DataInputStream(socketCliente.getInputStream());
                    DataOutputStream salida=new DataOutputStream(socketCliente.getOutputStream());
                ) {
                    System.out.println("Cliente recibido desde IP: "+socketCliente.getInetAddress().getHostAddress()+" con el puerto: "+socketCliente.getPort());
                    //funcion del servidor:
                    //recibir datos
                    //recibimos el tamaño del array
                    int length=entrada.readInt();
                    int[] array = new int[length];
                    //iteramos y escribimos en nuestro array cada numero recibido
                    for(int i=0;i<length;i++){
                        //leemos y asignamos al array
                        array[i]=entrada.readInt();
                    }
                    //calculo
                    int suma=0;
                    int mayor=-10000;
                    int menor=10000;
                    for(int i=0;i<length;i++){
                        //sumamos
                        suma+=array[i];
                        //mayor
                        if(array[i]>mayor)
                            mayor=array[i];
                        //menor
                        if(array[i]<menor)
                            menor=array[i];
                    }
                    //enviarDatos
                    //para enviar los 3 datos en un solo paquete lo uniremos por un / y asi el cliente los podra separar
                    String mensaje=String.valueOf(suma)+"/"+String.valueOf(mayor)+"/"+String.valueOf(menor);
                    System.out.println(mensaje);
                    salida.writeUTF(mensaje);

                    //finalizamos con el cliente y esperamos al siguiente
                    System.out.println("Cliente finalizado, esperando al siguiente....");

                }catch(IOException eCliente){
                System.out.println("Error atendiendo al cliente: "+eCliente.getMessage());
                eCliente.printStackTrace();
                }
            }  
        }catch(IOException e){
            System.out.println("Error en el servidor: "+e.getMessage());
            e.printStackTrace();
        }    
    }
}

