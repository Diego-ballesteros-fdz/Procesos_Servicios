


import java.net.*;
import java.util.Scanner;


import java.io.*;

public class TCPCliente1 {
    public static void main(String[] args) {
        
        Scanner teclado=new Scanner(System.in);
        int puerto=55555;

        try{

        InetAddress ip=InetAddress.getByName("192.168.204.129");
        
        Socket cliente1=new Socket(ip,puerto);

        String mensaje = "Diego Ballesteros";

        //creamos el flujo de salido
        DataOutputStream flujoSalida = new DataOutputStream(cliente1.getOutputStream());
        //escribimos en el flujo de salida el nombre
        flujoSalida.writeInt(50258682);

    
        
        DataInputStream flujoEntrada=new DataInputStream(cliente1.getInputStream());

        System.out.println(flujoEntrada.readUTF());

        //cerramos
        flujoSalida.close();	
        flujoEntrada.close();
	    cliente1.close();

        


        }catch(IOException e){System.out.println(e);}

    }
    
}
