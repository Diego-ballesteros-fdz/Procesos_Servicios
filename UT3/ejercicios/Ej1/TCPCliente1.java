import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TCPCliente1 {
    public static void main(String[] args) {
        
        Scanner teclado=new Scanner(System.in);
        int puerto=55555;

        try{

        InetAddress ip=InetAddress.getByName("192.168.1.37");
        
        Socket cliente1=new Socket(ip,puerto);
        System.out.println("Escriba un numero para calcular el factorial: ");

        int numero = teclado.nextInt();
        String mensaje=String.valueOf(numero);

        //creamos el flujo de salida
        DataOutputStream flujoSalida = new DataOutputStream(cliente1.getOutputStream());
        //escribimos en el flujo de salida el mensaje
        flujoSalida.writeUTF(mensaje);

    
        //Creamos el flujo de entrada
        DataInputStream flujoEntrada=new DataInputStream(cliente1.getInputStream());
        //recibimos el mensaje
        System.out.println(flujoEntrada.readUTF());

        //cerramos
        flujoSalida.close();	
        flujoEntrada.close();
	    cliente1.close();

        


        }catch(IOException e){System.out.println(e);}

    }
    
}
