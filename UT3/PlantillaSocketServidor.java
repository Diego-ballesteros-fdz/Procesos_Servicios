package Procesos_Servicios.UT3;

import java.io.IOException;
import java.net.*;

public class PlantillaSocketServidor {
    //para TCP
    public static void main(String[] args) {
             int puerto=5555;
    try 
        {
        ServerSocket Servidor = new ServerSocket(puerto); 
        
        Socket cliente1= Servidor.accept(); 
        System.out.println("Cliente 1 conectado");
        /* Aquí irían todas las acciones a realizar con el cliente1
         
         */
         Servidor.close(); //Cierre del socket

        } catch (IOException excepcion) {excepcion.getMessage()	;  }
    }
}