import java.util.ArrayList;
import java.util.Scanner;

public class FilaSuper {
    /**
     * metodo main donde se encontraran los clientes(Hilos)
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Caja> cajas=new ArrayList<Caja>();
        ArrayList<HiloCliente> clientes=new ArrayList<>();
        Scanner teclado=new Scanner(System.in);
        //creamos n cajas
        System.out.println("¿Cuantas cajas quiere?");
        int n=teclado.nextInt();
        for(int i=0;i<n;i++){
            Caja c=new Caja(i+1, false);
            cajas.add(c);
        }
        //creamos los m clientes
        System.out.println("¿Cuantos clientes quiere?");
        int m=teclado.nextInt();
        for(int i=0;i<m;i++){
            HiloCliente h=new HiloCliente(i+1, cajas);
            clientes.add(h);
        }
        //abrimos el super e iniciamos los hilos cliente
        System.out.println("Abre el supermercado");
        for(int i=0;i<clientes.size();i++){
            HiloCliente c=clientes.get(i);
            c.start();
        }

        //esperamos a q acaben todos los hilos cliente y cerramos el super
        try{
            for(int i=0; i<clientes.size(); i++) {
                HiloCliente c=clientes.get(i);
                c.join();
            } 
        }catch(Exception e){
            System.out.println(e);
        }      
        System.out.println("Cierra el supermercado");

        

    }
}
