import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCliente2 {

    public static void main(String[] args){     
        try{
        InetAddress ip=InetAddress.getByName("192.168.1.37");
        int puerto = 5555;

        DatagramSocket clienteSocket=new DatagramSocket(puerto);

        byte[] bufer=new byte[1024];
        DatagramPacket recibo=new DatagramPacket(bufer,bufer.length);
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

        byte[] recibidos2=new byte[1024];
        recibidos2=enviar.getBytes();//pasamos a bytes y machacamos lo anterior

        //preparamos el envio
        DatagramPacket envio=new DatagramPacket(recibidos2, recibidos2.length,ip,55555);
        System.out.println("Cliente 2 envia el factorial "+factorial);
        //hacemos el envio
        clienteSocket.send(envio);
         }catch(Exception e){
            System.out.println(e);
        }
    }
    
}
