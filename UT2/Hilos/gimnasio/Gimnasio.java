
public class Gimnasio {
    Sala[] salas=new Sala[5];


    public Gimnasio(){
        for(int i=0;i<salas.length;i++){
            salas[i]=new Sala(i+1);
        }
    }

    public void entrar(int idSala,String nombre){
        salas[idSala].entrenar(nombre);//entramos a entrenar
    }

    public synchronized void salir(int i){
        salas[i].estado=true;//liberamos la sala
        notifyAll();
    }

    /**
     * metodo en el que entraran los hilos clientes y esperaran una sala
     */
    public synchronized int asignarSala(){
        boolean atendido=false;
        while(!atendido){
            for(int i=0;i<salas.length;i++){
                //si esta libre devolvemos la sala que esta libre
                if(salas[i].estado==true){
                    salas[i].estado=false;//reservamos la sala
                    return i;
                }
            }
            try {
                wait();//esperamos al notify
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //nunca llegaremos aqui
        return -1;
    }

    
}
