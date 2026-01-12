import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class TCPCliente {
    public static void main(String[] args) {
        boolean salir=false;
        int cont=1;
        Scanner teclado=new Scanner(System.in);
        int pid;
        String mensaje;
        try(Socket cliente=new Socket(InetAddress.getByName("192.168.1.41"),55555)){
            //creamos el flujo de salida
            ObjectOutputStream flujoSalida=new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream flujoEntrada=new ObjectInputStream(cliente.getInputStream());
            //preparamos el bucle del juego
            while(!salir){
                //solicitamos el numero para adivinar
                System.out.println("Ronda "+cont+": \n \t Indique el número que cree correcto:");
                pid=teclado.nextInt();
                //enviamos al server
                flujoSalida.writeObject(pid);
                flujoSalida.flush();
                //recibimos el mensaje
                mensaje=String.valueOf(flujoEntrada.readObject());
                //verificamos que acaba el string con ganado.
                if(mensaje.endsWith("ganado.")){
                    //paramos el bucle
                    salir=true;
                }
                //mostramos el mensaje
                System.out.println(mensaje);
                //subimos la ronda
                cont++;
                //reseteamos los flujos
                flujoSalida.reset();
                flujoEntrada.reset();
            }
            cliente.close();
        }catch(SocketException eSocket){
            System.out.println("Usted a perdido");
        }catch(Exception e){
            System.out.println("Algo salio mal en el cliente: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
}
