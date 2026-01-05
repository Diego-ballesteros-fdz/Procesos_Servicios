import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServidor {
    public static void main(String[] argv) throws Exception {
    byte[] bufer = new byte[1024];//bufer para recibir el datagrama  
    //Asociacion del Socket al puerto
    DatagramSocket socket = new DatagramSocket(55555);   

    //Socket a la escucha ...
    System.out.println("Esperando Datagrama .......... ");  
    DatagramPacket recibo = new DatagramPacket(bufer, bufer.length);
    //Recepcion datagrama
    socket.receive(recibo);
    int bytesRec = recibo.getLength();
    //convertimos a String
    String paquete= new String(recibo.getData());
    System.out.println("Recibido del Cliente 1 ,"+paquete+" con ip: "+recibo.getAddress()+"en el puerto: "+recibo.getPort());

    //alamacenamos la info del cliente 1
    InetAddress ip1=recibo.getAddress();
    int puerto1=recibo.getPort();

    //ahora debemos enviar al cliente 2
    InetAddress ip2= InetAddress.getByName("192.168.1.38");
    int puerto2=5555;
    // ENVIANDO DATAGRAMA AL CLIENTE2
    System.out.println("Enviando numero al cliente 2 para calcular su factorial " + paquete);
    bufer=paquete.getBytes();
    //preparamos el envio
    DatagramPacket envio = new DatagramPacket(bufer, bufer.length, ip2, puerto2);
    //enviamos
    socket.send(envio);

    //debemos recibir del cliente 2 sobreescribiendo el datagram recibo
    socket.receive(recibo);
    //capturamos en un string
    String factorial=new String(recibo.getData());
    System.out.println("Recibiendo del cliente 2 el factorial: "+factorial);

    //Enviamos al Cliente 1
    bufer=factorial.getBytes();
    DatagramPacket envio2=new DatagramPacket(bufer,bufer.length,ip1, puerto1);
    socket.send(envio2);
    
    socket.close(); 
  }
    
}
