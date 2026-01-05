import java.net.*;
import java.util.Scanner;


public class UDPCliente1 {
    public static void main(String[] args) throws Exception {
            Scanner teclado=new Scanner(System.in);
            byte[] recibidos=new byte[1024];
            InetAddress ip=InetAddress.getByName("192.168.1.40");
            int puerto = 55555;

            DatagramSocket clienteSocket=new DatagramSocket(puerto);

            System.out.println("Escribe un numero para calcular su factorial: ");
            int numero=teclado.nextInt();
            
            String enviar=String.valueOf(numero);//pasamos a string
            recibidos=enviar.getBytes();//pasamos a bytes
            //preparamos el envio
            DatagramPacket envio=new DatagramPacket(recibidos, recibidos.length,ip,puerto);
            System.out.println("Cliente 1 envia el numero "+numero);
            //hacemos el envio
            clienteSocket.send(envio);
            
            //recibimos del servidor
            DatagramPacket recibo=new DatagramPacket(recibidos,recibidos.length,ip,puerto);
            clienteSocket.receive(recibo);//recibimos el mensaje del server para trabajar con él
            String mensaje=new String(recibo.getData()).trim();
            System.out.println("El cliente 1 recibe del server el factorial: "+ mensaje);
      
    }
    
}
