import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    public static void main(String[] args) throws IOException{
        
        DatagramSocket socket1=new DatagramSocket(5555);//servidor en local

        System.out.println("Servidor en espera....");
        DatagramPacket recibo;

        byte[] bufer = new byte[1024];
        recibo = new DatagramPacket(bufer, bufer.length);
        socket1.receive(recibo);//recibo los datos

        String mensaje=new String(recibo.getData()).trim();//recojo el strim del dataPack

        System.out.println("Servidor recibe "+mensaje+" del cliente 1");

         DatagramSocket socket2=new DatagramSocket(6666);//servidor en local
         InetAddress ip=InetAddress.getByName("192.168.102");
        //realizamos la conexion con el cliente 2
        DatagramPacket envio= new DatagramPacket(bufer, bufer.length,ip,6666);//preparamos el mensaje
        socket2.send(envio);//envimos el mensaje

        //recogemos el mensaje del cliente 2
        recibo = new DatagramPacket(bufer, bufer.length);
        socket2.receive(recibo);//recibo los datos
        mensaje=new String(recibo.getData()).trim();//recojo el strim del dataPack

        System.out.println("Servidor recibe "+mensaje+" del cliente 2");

        //se lo pasamos al cliente 1 para que muestre dicho contenido
        bufer=mensaje.getBytes();//preparamos el buffer
        ip=InetAddress.getByName("192.168.204.102");
        envio= new DatagramPacket(bufer, bufer.length,ip,5555);
        socket1.send(envio);


    }
    
}
