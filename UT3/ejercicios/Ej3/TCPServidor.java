import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServidor {
    public static void main(String[] args) {
        //creacion del socket
        try(ServerSocket server=new ServerSocket(55555)){
            System.out.println("Servidor a la escucha....");
            //aceptamos al cliente
            Socket cliente= server.accept();

            //creamos el flujo de entrada
            ObjectInputStream flujoEntrada=new ObjectInputStream(cliente.getInputStream());
            //recibimos el obj
            Factura facRecibida=(Factura) flujoEntrada.readObject();

            //hacemos los calculos
            facRecibida.calcularImporteTotal();

            System.out.println("Calculos rrealizados.");
            System.out.println(facRecibida.toString());

            //enviar el objeto
            ObjectOutputStream flujoSalida=new ObjectOutputStream(cliente.getOutputStream());
            flujoSalida.writeObject(facRecibida);
            flujoSalida.flush();

            System.out.println("El sercidor a acabado su trabajo");
            cliente.close();
            server.close();          
        }catch(Exception eServer){
            System.out.println("Algo salio mal con el servidor: "+eServer.getMessage());
            eServer.printStackTrace();
        }
    }
}
