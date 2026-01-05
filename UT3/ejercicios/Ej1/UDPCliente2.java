import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCliente2 {

    public static void main(String[] args) throws IOException{
        
        DatagramSocket clienteSocket=new DatagramSocket();

        InetAddress ip=InetAddress.getByName("192.168.1.40");
        int puerto = 55555;

        byte[] recibidos=new byte[1024];
        DatagramPacket recibo=new DatagramPacket(recibidos,recibidos.length,ip,puerto);
        clienteSocket.receive(recibo);//recibimos el mensaje del server para trabajar con él

        String mensaje=new String(recibo.getData()).trim();
        System.out.println("El cliente 2 recibe del server: "+ mensaje);
        int num= Integer.valueOf(mensaje);//transformamos a int
        //calculamos el factorial
        int factorial=1;
        for (int i = 1; i <= num; i++) {
            factorial *= i;
        }
        String enviar=String.valueOf(factorial);//pasamos a string

        recibidos=enviar.getBytes();//pasamos a bytes y machacamos lo anterior

        //preparamos el envio
        DatagramPacket envio=new DatagramPacket(recibidos, recibidos.length,ip,puerto);
        System.out.println("Cliente 2 envia el factorial "+factorial);
        //hacemos el envio
        clienteSocket.send(envio);
    }
    
}
