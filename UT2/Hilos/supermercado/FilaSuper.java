import java.lang.reflect.Array;

public class FilaSuper {
    /**
     * metodo main donde se encontraran los clientes(Hilos)
     * @param args
     */
    public static void main(String[] args) {
        Supermercado s=new Supermercado();
        HiloCliente clientes[]=new HiloCliente[15];
        //creamos los 15 clientes
        for(int i=1;i<=15;i++){
            HiloCliente h=new HiloCliente(i, s);
            clientes[i-1]=h;
        }
        //abrimos e super y iniciamos los hilos cliente
        System.out.println("Abre el supermercado");
        for(int i=0;i<clientes.length;i++){
            clientes[i].start();
        }

        //esperamos a q acaben todos los hilos cliente y cerramos el super
        try{
            for(int i=0; i<clientes.length; i++) {
                clientes[i].join();
            } 
        }catch(Exception e){
            System.out.println(e);
        }      
        System.out.println("Cierra el supermercado");

        

    }
}
