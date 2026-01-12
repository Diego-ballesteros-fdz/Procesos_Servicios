import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Cliente extends Thread{
    private int pid;
    private float pidServer;
    private boolean salir;
    private boolean[] mismaronda;
    private Socket cliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private int cont;
    private ArrayList<Cliente> listaThread;

    public Cliente(Socket cliente,float pidServer,ArrayList<Cliente> listaThread){
        this.pidServer=pidServer;
        this.cliente=cliente;
        salir=false;
        cont=1;
        this.listaThread=listaThread;
        mismaronda= new boolean[] {true,true,true,true};
    }
    public int getCont(){
        return cont;
    }
    
    public void run(){
        try{
        //creamos flujos
        System.out.println("Cliente creado correctamente");
        entrada=new ObjectInputStream(cliente.getInputStream());
        salida=new ObjectOutputStream(cliente.getOutputStream());
        while(!salir){
            //comprobamos si estamos todos en la misma ronda
            for(int i=0;i<listaThread.size();i++){
                Cliente h=listaThread.get(i);
                if(h.getCont()==cont){
                    mismaronda[i]=true;
                }
            }
            //System.out.println(Arrays.toString(mismaronda));
            if(mismaronda[0] && mismaronda[1] && mismaronda[2] && mismaronda[3]){
            //recibir
            String mensaje=String.valueOf(entrada.readObject());
            pid=Integer.valueOf(mensaje);
            System.out.println("Recibido en el thread "+this.getId()+"el numero "+pid);
            //comprobar y salir en caso necesario
            if(pid>pidServer){
                //el numero es mayor
                mensaje="El pid del Servidor es menor.";
            }else if(pid<pidServer){
                //el numero es menor
                mensaje="El pid del Servidor es mayor.";
            }else{
                //el numero es igual
                salir=true;
                mensaje="El pid del Servidor es el que has dicho."+"\n"+"\t ¡Felicidades! Has ganado.";
            }
            //devolver respuesta
            salida.writeObject(mensaje);
            salida.flush();
            System.out.println("LLegamos aqui");
            //sumamos ronda
            cont++;
            //reseteamos la bandera de siguiente ronda
            for(int i=0;i<mismaronda.length;i++){
                mismaronda[i]=false;
            }
        }
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
