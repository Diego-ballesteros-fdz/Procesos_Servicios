import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente extends Thread{
    private int pid;
    private int pidServer;
    private boolean salir;
    private Socket cliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public Cliente(Socket cliente,int pidServer){
        this.pidServer=pidServer;
        this.cliente=cliente;
        salir=false;
    }
    
    public void run(){
        try{
        //creamos flujos
        entrada=new ObjectInputStream(cliente.getInputStream());
        salida=new ObjectOutputStream(cliente.getOutputStream());
        while(!salir){
            //recibir
            String mensaje=String.valueOf(entrada.readObject());
            pid=Integer.valueOf(mensaje);
            System.out.println("Recibido en el thread "+this.getId()+"el numero "+pid);
            //comprobar y salir en caso necesario
            if(pid>pidServer){
                //el numero es mayor
                mensaje="El pid del Servidor es mayor.";
            }else if(pid<pidServer){
                //el numero es menor
                mensaje="El pid del Servidor es menor.";
            }else{
                //el numero es igual
                salir=true;
                mensaje="El pid del Servidor es el que has dicho."+"\n"+"\t ¡Felicidades! Has ganado.";
            }
            //devolver respuesta
            salida.writeObject(mensaje);
            salida.flush();
        }
        //si estamos aqui esque nuestro hilo ha ganado, cerramos para avisar al server
        entrada.close();
        salida.close();
        cliente.close();

        //en el catch capturar:
        //SocketException para cuando el server cierre al perder
        //Exception por si algo falla
        }catch(SocketException eSocket){
            try {
                if (cliente != null && !cliente.isClosed()) {
                    cliente.close();
                }
            } catch (IOException ex) {
                // Silencio
            }
        }catch(Exception e){
            //Algo salio mal para depurar pondremos el posible error
            System.out.println("Algo salio mal en el server o hemos perdido: "+e.getMessage());
        }
    }
}
