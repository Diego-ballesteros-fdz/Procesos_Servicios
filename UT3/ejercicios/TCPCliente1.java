


import java.net.*;
import java.util.Scanner;


import java.io.*;

public class TCPCliente1 {
    public static void main(String[] args) {
        
        Scanner teclado=new Scanner(System.in);
        int puerto=5555;

        try{

        InetAddress ip=InetAddress.getByName("192.168.204.102");
        
        Socket cliente1=new Socket(ip,puerto);

        System.out.println("Escriba un numero para enviar al servidor: ");

        int mensaje=teclado.nextInt();
        
        //creamos el flujo de salido
        DataOutputStream flujoSalida = new DataOutputStream(cliente1.getOutputStream());
        //escribimos en el flujo de salida
        flujoSalida.writeUTF(String.valueOf(mensaje));

        
        DataInputStream flujoEntrada=new DataInputStream(cliente1.getInputStream());

        System.out.println("Factorial: "+flujoEntrada.readUTF()+" recibido del servidor.");

        //cerramos
        flujoSalida.close();	
        flujoEntrada.close();
	    cliente1.close();

        


        }catch(IOException e){System.out.println(e);}

    }
    
}
