import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPCliente {
    public static void main(String[] args) {
        try(Socket cliente=new Socket(InetAddress.getByName("192.168.1.41"),55555)){
            //creamos el flujo de salida
            ObjectOutputStream flujoSalida=new ObjectOutputStream(cliente.getOutputStream());
            //preparamos el obj para ser enviado
            flujoSalida.writeObject("Hola Server");
            flujoSalida.flush();//ordenamos que se envie y vacio el bufferStream
            System.out.println("Enviado el obj");
            cliente.close();
        }
    }
    
}
