import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class UDPServidor {
    public static void main(String[] args) {
        //creacion del socket
        int puerto= 55555;
        try(DatagramSocket server=new DatagramSocket(puerto)){
            //creacion de buffer y datgrampackets
            byte[] bufer=new byte[1024];
            DatagramPacket entrada=new DatagramPacket(bufer,bufer.length);
            DatagramPacket salida;
            //server infinito
            while(true){
                System.out.println("Esperando paquetes....");
                //recibir datos
                //recibimos el tamaño del array
                server.receive(entrada);
                //guardamos datos
                InetAddress ip= entrada.getAddress();
                int port=entrada.getPort();
                String strEntrada=new String(entrada.getData()).trim();
                int length=Integer.valueOf(strEntrada);
                //limpiamos bufer
                Arrays.fill(bufer,(byte)0);
                //rehacemos el datgrampacket
                entrada=new DatagramPacket(bufer, bufer.length);
                //recibimos iterando los numeros
                int[] array=new int[length];

                for(int i=0;i<length;i++){
                    //recibimos
                    server.receive(entrada);
                    strEntrada=new String(entrada.getData()).trim();
                    array[i]=Integer.valueOf(strEntrada);
                    System.out.println("Recibido: "+array[i]);
                    //limpiamos bufer
                    Arrays.fill(bufer,(byte)0);
                    //rehacemos el datgrampacket
                    entrada=new DatagramPacket(bufer, bufer.length);
                }
                //calculo
                int suma=0,mayor=-1000,menor=1000;
                for(int i=0;i<array.length;i++){
                    suma+=array[i];
                    if(array[i]<menor)
                        menor=array[i];
                    if(array[i]>mayor)
                        mayor=array[i];
                }
                //enviar datos
                //Creamos un mensaje que luego dibidiremos usando /
                String mensaje=String.valueOf(suma)+"/"+String.valueOf(menor)+"/"+String.valueOf(mayor);
                System.out.println(mensaje);
                //enviamos usando los datos de la entrada
                byte[]bufer2=new byte[1024];
                bufer2=mensaje.getBytes();
                salida=new DatagramPacket(bufer2, bufer2.length,ip,port);
                server.send(salida);
                System.out.println("Cliente finalizado, esperando al siguiente...");
                //limpiamos bufer
                Arrays.fill(bufer,(byte)0);
            }


        }catch(IOException e){
            System.out.println("Problema al crear el server: "+e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
