package Procesos_Servicios.UT3.ejercicios.Ej4.Proposal;

public class Application {

    private static final int PORT = 55555;
    private static final String IP_SERVER = "192.168.1.41";

    public static void main(String[] args) {
        new TCPClient(IP_SERVER, PORT); // This starts the client and game loop. It's a double behavior bad practice.
    }
}
