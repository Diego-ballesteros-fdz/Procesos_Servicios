

public class Caja {

    boolean estado;
    String nombre;

    public Caja(int id,boolean estado){
        this.estado=estado;
        this.nombre="Caja"+id;
    }

    synchronized void atender(String nombre){
        //ocupamos la caja
        estado=true;
        System.out.println(nombre+" Entrando a la "+this.nombre);
        //esperamos 5 seg
        try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
        System.out.println(nombre+" saliendo de la "+this.nombre);
        //liberamos la caja
        estado=false;
    }

    public boolean getEstado(){
        return estado;
    }



}
