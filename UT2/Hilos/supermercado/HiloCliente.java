

public class HiloCliente extends Thread {

    String nombre;
    Supermercado s;

    public HiloCliente(int num, Supermercado s){
        this.nombre="Cliente"+num;
        this.s=s;
    }

    
    public void run(){
        int caja=-1;

        do{
            caja=s.cajaLibre();
        }while(caja==-1);

        s.recibirCliente(caja,nombre);
    }

    public String getNombre(){
        return nombre;
    }
    
}
