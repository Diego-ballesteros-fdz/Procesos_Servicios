import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UDPCliente {
    public static void main(String[] args) {
        //creamos el socket
        
        byte[] bufer=new byte[1024];
        try(DatagramSocket cliente=new DatagramSocket(4444)){
            //crear el array
            int[] array=new int[Aleatorio(5,10)];
                for(int i=0;i<array.length;i++){
                    array[i]=Aleatorio(-10, 10);
                    System.out.print(array[i]);
                    if(i==array.length-1){
                        System.out.println(".");
                    }else{
                        System.out.print(", ");
                    }
                }
                System.out.println("Array creado con exito: ");
            //enviar
            InetAddress ip=InetAddress.getByName("192.168.1.40");
            int puerto= 55555;
            //primero enviamos el tamaño como String
            bufer=String.valueOf(array.length).getBytes();
            DatagramPacket salida=new DatagramPacket(bufer, bufer.length,ip,puerto);
            cliente.send(salida);
            //ahora iteramos el envio del array
            for(int i=0;i<array.length;i++){
                    bufer=String.valueOf(array[i]).getBytes();
                    salida=new DatagramPacket(bufer, bufer.length,ip,puerto);
                    cliente.send(salida);
                    //limpiamos bufer
                    Arrays.fill(bufer, (byte) 0);
                }
                System.out.println("Enviado el array con exito");
            //recibir
            byte[] bufer2 = new byte[1024];
            DatagramPacket entrada=new DatagramPacket(bufer2, bufer2.length);
            cliente.receive(entrada);
            String mensaje=new String(entrada.getData()).trim();
            System.out.println("mensaje recibido del server: "+mensaje);
            String[] datos=new String[3];
            datos=mensaje.split("/");
            //mostramos el mensaje
            System.out.println("La suma del array es: "+datos[0]+", el mayor es: "+datos[1]+", y el menor es: "+datos[2]+".");

            cliente.close();

        }catch(IOException e){
            System.out.println("Algo fallo en la conexion del cliente: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static int Aleatorio(int min,int max){
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
