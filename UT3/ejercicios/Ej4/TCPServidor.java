import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;

public class TCPServidor {
    public static void main(String[] args) {
        final int puerto = 55555;
        ArrayList<Socket> listaCliente=new ArrayList<>();
        ArrayList<Cliente> listaThread=new ArrayList<>();
        float pid=ProcessHandle.current().pid();
        System.out.println("El pid es: "+pid);

        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);
            Socket clienteSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());
            //guardamos a cada cliente para saber si estan todos conectados
            listaCliente.add(clienteSocket);
            while (true) {
                if(listaCliente.size()==4){
                    //verificamos que ninguno haya ganado
                    int SocketGanador=-1;
                    boolean ganador=false;
                    for(int i=0;i<listaCliente.size();i++){
                        Socket cliente= listaCliente.get(i);
                        //comprobamos si ha sido cerrado
                        if(cliente.isClosed()==true){
                            //guardamos la pos del ganador
                            SocketGanador=i;
                            //salimos del juego desconectando el resto
                            ganador=true;
                        }
                    }
                    if(ganador){
                        listaCliente.remove(listaCliente.get(SocketGanador));
                        //desconectamos al resto
                        for(Socket c:listaCliente){
                            //eliminamos conexion (Lo cual hara que los clientes lancen el mensaje de perdedor en el catch)
                            c.close();
                        }
                        //limpiamos la lista de sockets
                        listaCliente.clear();
                        System.out.println("Juego acabado");
                    }   
                }else{
                    int quedan=4-listaCliente.size();
                    System.out.println("Esperando a: "+quedan+" jugador/es.");
                    //aceptamos a otro
                    clienteSocket = serverSocket.accept();
                    System.out.println("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());
                    //guardamos a cada cliente para saber si estan todos conectados
                    listaCliente.add(clienteSocket);
                    if(listaCliente.size()==4){
                        System.out.println("Iniciando partida");
                        for(Socket c:listaCliente){
                            // Crear un nuevo hilo para manejar la conexión con el cliente
                            Cliente cliente = new Cliente(c,pid,listaThread);
                            listaThread.add(cliente);
                        }
                        for(Thread c:listaThread){
                            //comenzar la partida
                            c.start();
                        }
                        System.out.println("Clientes iniciados correctamente");
                        
                    }
                }
                //esperamos un segundo para no saturar el procesador(Los hilos seguiran con su comunicación)
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

