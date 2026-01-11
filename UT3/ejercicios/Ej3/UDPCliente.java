import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class UDPCliente {
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
        teclado.nextLine();
        System.out.println("Introduzca el Tipo de iva (IGC,ESP,EUR): ");
        String tipoIva=teclado.nextLine();
        //creamos el obj Factura
        Factura fac=new Factura(numFact, fecha, importe, tipoIva);
        
        //cremos el socket
        try(DatagramSocket cliente=new DatagramSocket(44444)){
            InetAddress ip=InetAddress.getByName("192.168.1.41");
            int port=55555;
            //preparamos el obj para ser enviado
            ByteArrayOutputStream bytOut=new ByteArrayOutputStream();
            ObjectOutputStream salida=new ObjectOutputStream(bytOut);
            salida.writeObject(fac);
            salida.flush();//para ordenar enviar ya al Out
            salida.close();
            byte[] bufer=bytOut.toByteArray();//añadimos la info en byte del objeto al array bufer(El que pasaremos)
            //creamos el flujo de salida
            DatagramPacket paqueteEnvio=new DatagramPacket(bufer,bufer.length,ip, port);
            //enviamos
            cliente.send(paqueteEnvio);
            System.out.println("Enviado el obj Fact");
            //recibimos el obj con su importe total e iva calculados
            //creamos la entrada
            byte[] bufer2=new byte[1024];
            DatagramPacket paqueteRecibo=new DatagramPacket(bufer2, bufer2.length);
            cliente.receive(paqueteRecibo);
            System.out.println("Recibido el paquete del server");
            //convertimos a fact
            ByteArrayInputStream bytIn = new ByteArrayInputStream(paqueteRecibo.getData());
            ObjectInputStream entrada = new ObjectInputStream(bytIn);
            Factura facRecibida=(Factura) entrada.readObject();
            //imprimos la factura
            System.out.println("Factura recibida.");
            System.out.println(facRecibida.toString());
            cliente.close();
            entrada.close();

        }catch(Exception e){
            System.out.println("Algo salio mal con el cliente: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
}
