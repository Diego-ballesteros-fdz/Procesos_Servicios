

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class TCPCliente2 {
    public static void main(String[] args) {
        
    int puerto=5555;

        try{

        InetAddress ip=InetAddress.getByName("192.168.204.115");
        
        Socket cliente2=new Socket(ip,puerto);
        
        
        // Creación flujo de entrada desde el servidor
        DataInputStream flujoEntrada = new  DataInputStream(cliente2.getInputStream());
        String mensaje =flujoEntrada.readUTF();
        System.out.println("Cliente 2 recibe "+mensaje);
       	int num=Integer.parseInt(mensaje);

	    int factorial=1;

        for (int i = 1; i <= num; i++) {
            factorial *= i;
        }

	    //devolvemos los datos a traves del canal de salida
        DataOutputStream flujoSalida = new DataOutputStream(cliente2.getOutputStream());
        //escribimos en el flujo de salida
        flujoSalida.writeUTF(String.valueOf(factorial));
        System.out.println("Cliente 2 envia al servidor: "+factorial);

        //cerramos
        flujoEntrada.close();
        flujoSalida.close();
	    cliente2.close();

        


        }catch(IOException e){System.out.println(e);}
    }
    
}
