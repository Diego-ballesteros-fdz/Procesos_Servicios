
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class UDPServidor {
    public static void main(String[] args) {
        //creacion del socket
        try(DatagramSocket server=new DatagramSocket(55555)){
            System.out.println("Servidor a la escucha de paquetes....");
            //creamos el flujo de entrada
            byte[] bufer=new byte[1024];
            DatagramPacket paqueteRecibido=new DatagramPacket(bufer, bufer.length);
            //recibimos el paquete
            server.receive(paqueteRecibido);
            //convertimos el obj
            ByteArrayInputStream bytIn=new ByteArrayInputStream(paqueteRecibido.getData());
            ObjectInputStream entrada=new ObjectInputStream(bytIn);
            Factura facRecibida=(Factura) entrada.readObject();
            System.out.println("Factura recibida");
            //hacemos los calculos
            facRecibida.calcularImporteTotal();
            System.out.println("Calculos realizados.");
            System.out.println(facRecibida.toString());

            //preparamos el enivio
            ByteArrayOutputStream bytOut=new ByteArrayOutputStream();
            ObjectOutputStream salida=new ObjectOutputStream(bytOut);
            salida.writeObject(facRecibida);
            byte[] bufer2=bytOut.toByteArray();
            salida.flush();
            salida.close();
            //enviar el objeto
            InetAddress ip= paqueteRecibido.getAddress();
            int port=paqueteRecibido.getPort();
            DatagramPacket paqueteEnviar=new DatagramPacket(bufer2, bufer2.length,ip,port);
            server.send(paqueteEnviar);
            System.out.println("El servidor a acabado su trabajo");
            server.close();          
        }catch(Exception eServer){
            System.out.println("Algo salio mal con el servidor: "+eServer.getMessage());
            eServer.printStackTrace();
        }
    }
}
