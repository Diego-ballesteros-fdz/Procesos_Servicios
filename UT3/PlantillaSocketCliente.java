package Procesos_Servicios.UT3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PlantillaSocketCliente {
    //para TCP
    public static void main(String[] args) {
        String host="localhost";
        int puerto=5555;
            try 
            {
                Socket Cliente = new Socket(host,puerto); 

                Cliente.close(); //Cierre del socket

            } catch (IOException excepcion) {excepcion.getMessage()	;  }
    }

}
