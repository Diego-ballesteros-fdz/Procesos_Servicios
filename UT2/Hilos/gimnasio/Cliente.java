

public class Cliente extends Thread{

    Sala s; //recurso compartido
    String nombre;

    public Cliente(int id,Sala sala){
        this.nombre="Cliente"+id;
        this.s=sala;
    }
    
    public void run(){
        System.out.println(nombre+" Entrando a la cola del gimnasio.");
        if(s.libre()){
            s.entrenar(nombre);//entramos a entrenar
        }
        s.salir();//salimos y notificamos
    }
}
