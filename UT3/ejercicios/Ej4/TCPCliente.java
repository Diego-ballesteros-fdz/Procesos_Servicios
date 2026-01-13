import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
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
                System.out.print("Ronda "+cont+": \n \t Indique el número que cree correcto:");
                pid=teclado.nextInt();
                System.out.println();
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
            }
            cliente.close();
        }catch(Exception e){
            System.out.println("Usted a perdido.");
        }
    }
    
}
