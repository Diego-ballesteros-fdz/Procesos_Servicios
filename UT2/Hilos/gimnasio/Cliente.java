

public class Cliente extends Thread{

    Gimnasio g; //recurso compartido
    String nombre;

    public Cliente(int id,Gimnasio g){
        this.nombre="Cliente"+id;
        this.g=g;
    }
    
    public void run(){
        System.out.println(nombre+" Entrando a la cola del gimnasio.");
        int i=g.asignarSala();
        g.entrar(i, nombre);
        g.salir(i);//hacemos el notify
    }
}
