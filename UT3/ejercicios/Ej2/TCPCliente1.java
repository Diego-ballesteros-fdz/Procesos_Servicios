import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPCliente1 {
    public static void main(String[] args) {
        try{
            //crear el socket TCP
            InetAddress ip=InetAddress.getByName("192.168.1.40");
            int puerto = 55555;
            try(Socket cliente=new Socket(ip,puerto)){
                //crear el array aleatorio de 10 numeros enteros
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
                System.out.println("Array creado con exito: "+array);
                //enviar el array
                DataOutputStream salida=new DataOutputStream(cliente.getOutputStream());
                //enviamos el length
                int length=array.length;
                salida.writeInt(length);
                //enviamos de manera iterada el array
                for(int i=0;i<array.length;i++){
                    salida.writeInt(array[i]);
                }
                System.out.println("Array enviada con exito");
                //recibir el resultado
                DataInputStream entrada=new DataInputStream(cliente.getInputStream());
                String mensaje=entrada.readUTF();
                System.out.println("Mensaje recibido: "+mensaje);
                //separamso el mensaje
                String[] datos=new String[3];
                datos=mensaje.split("/");
                System.out.println("La suma del array es: "+datos[0]+", el mayor es: "+datos[1]+", y el menor es: "+datos[2]+".");
                
            }catch(IOException eCliente){
                System.out.println("Error al crear el socket: "+eCliente.getMessage());
                eCliente.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
    }

    public static int Aleatorio(int min,int max){
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}


