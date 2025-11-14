import java.util.ArrayList;

public class HiloCliente extends Thread {

    String nombre;
    ArrayList<Caja> cajas;

    public HiloCliente(int num, ArrayList<Caja> cajas){
        this.nombre="Cliente"+num;
        this.cajas=cajas;
    }

    
    public void run(){
        System.out.println(nombre+" Entra en la fila del supermercado.");
        boolean atendido=false;
        do{
            for(int i=0;i<cajas.size();i++){
                Caja c=cajas.get(i);
                if(c.getEstado()==false && atendido==false){
                    c.atender(nombre);
                    atendido=true;
                    break;
                }
                try {
                Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }while(!atendido);
    }

    public String getNombre(){
        return nombre;
    }
    
}
