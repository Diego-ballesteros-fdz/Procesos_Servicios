

public class Cola {
    public static void main(String[] args) {
        Sala s=new Sala();
        Cliente clientes[]=new Cliente[20];
        System.out.println("Gimnasio abierto");
        //inicializamos los clientes y los ejecutamos
        for(int i=0;i<clientes.length;i++){
            clientes[i]=new Cliente(i+1, s);
            clientes[i].start();
        }
        //esperamos a que acaben todos los hilos para acabar nuestro main
        for(int i=0;i<clientes.length;i++){
            try{
                clientes[i].join();
            }catch(Exception e){
                System.out.println(e);
            }
        }
        //indicamos que el gimnasio esta cerrado
        System.out.println("Gimnasio cerrado");
    }
}
