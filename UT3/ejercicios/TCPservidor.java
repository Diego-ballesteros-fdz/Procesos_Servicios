

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPservidor {
     public static void main(String[] args) {
             int puerto=5555;
    try 
        {
        ServerSocket Servidor = new ServerSocket(puerto); 
        
        Socket cliente1= Servidor.accept(); 
        System.out.println("Cliente 1 conectado");
        
        //recibimos el mensaje del cliente1
        InputStream entrada= cliente1.getInputStream();
        DataInputStream flujoEntrada=new DataInputStream(entrada);

        System.out.println("Recibiendo del cliente 1 "+flujoEntrada.readUTF());

        Socket cliente2=Servidor.accept();
        
        //enviamos el menasje al cliente 2 para que haga sus cosas
        OutputStream salida= cliente2.getOutputStream();
        DataOutputStream flujoSalida = new DataOutputStream(salida);
        flujoSalida.writeUTF(flujoEntrada.readUTF());
        String mensaje=flujoEntrada.readUTF();

        System.out.println("enviando al cliente 2: " +mensaje);

        //leemos el mensaje del cliente 2
        InputStream entrada2=cliente2.getInputStream();
        DataInputStream flujoEntrada2=new DataInputStream(entrada2);
        String mensaje2=flujoEntrada2.readUTF();

        System.out.println("Recibiendo del cliente 2 "+mensaje2+", enviandolo al cliente 1");

        //enviamos el menasje al cliente 2 para que haga sus cosas
        OutputStream salida2= cliente1.getOutputStream();
        DataOutputStream flujoSalida2 = new DataOutputStream(salida2);
        flujoSalida2.writeUTF(mensaje2);

        System.out.println("Enviado desde el server al cliente 1 "+mensaje2);


        //cerramos todo lo creado para el ejercicio
        cliente1.close();
        cliente2.close();
        flujoSalida.close();
        flujoSalida2.close();
        flujoEntrada.close();
        flujoEntrada2.close();
        Servidor.close(); 

        } catch (IOException excepcion) {excepcion.getMessage()	;  }
    }
    
}
