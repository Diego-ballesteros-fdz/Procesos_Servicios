import java.net.*;
import java.util.Scanner;


public class UDPCliente1 {
    public static void main(String[] args) {
        try{
            Scanner teclado=new Scanner(System.in);
            byte[] bufer=new byte[1024];
            InetAddress ip=InetAddress.getByName("192.168.1.37");
            int puerto = 55555;

            DatagramSocket clienteSocket=new DatagramSocket(4444);

            System.out.println("Escribe un numero para calcular su factorial: ");
            int numero=teclado.nextInt();
            
            String enviar=String.valueOf(numero);//pasamos a string
            bufer=enviar.getBytes();//pasamos a bytes
            //preparamos el envio
            DatagramPacket envio=new DatagramPacket(bufer, bufer.length,ip,puerto);
            System.out.println("Cliente 1 envia el numero "+numero);
            //hacemos el envio
            clienteSocket.send(envio);
            System.out.println("Enviado del cliente 1");
            
            //recibimos del servidor
            byte[] bufer2=new byte[1024];
            DatagramPacket recibo=new DatagramPacket(bufer2,bufer2.length);
            clienteSocket.receive(recibo);//recibimos el mensaje del server para trabajar con él
            String mensaje = new String(recibo.getData()).trim();
            System.out.println("El cliente 1 recibe del server el factorial: "+ mensaje);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
}
