import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPCliente {
    public static void main(String[] args) {
        Scanner teclado=new Scanner(System.in);
        
        //pedimos los datos
        System.out.println("Añadiendo una factura nueva:");
        System.out.println("Introduzca el numero ID: ");
        String numFact=teclado.nextLine();
        System.out.println("Introduzca la fecha: ");
        String fecha=teclado.nextLine();
        System.out.println("Introduzca el importe: ");
        double importe=teclado.nextDouble();
        System.out.println("Introduzca el Tipo de iva (IGC,ESP,EUR): ");
        String tipoIva=teclado.nextLine();
        //creamos el obj Factura
        Factura fac=new Factura(numFact, fecha, importe, tipoIva);
        //cremos el socket
        try(Socket cliente=new Socket(InetAddress.getByName("192.168.1.38"),55555)){
            //creamos el flujo de salida
            ObjectOutputStream flujoSalida=new ObjectOutputStream(cliente.getOutputStream());
            //preparamos el obj para ser enviado
            flujoSalida.writeObject(fac);
            flujoSalida.flush();//ordenamos que se envie y vacio el bufferStream
            System.out.println("Enviado el obj Fact");
            //recibimos el obj con su importe total e iva calculados
            //creamos la entrada
            ObjectInputStream flujoEntrada=new ObjectInputStream(cliente.getInputStream());
            Factura facRecibida=(Factura) flujoEntrada.readObject();
            //imprimos la factura
            System.out.println("Factura recibida.");
            System.out.println(facRecibida.toString());
            cliente.close();

        }catch(IOException e){
            System.out.println("Algo salio mal con el cliente: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
}
