

public class Caja {

    boolean estado;
    String nombre;

    public Caja(String nombre,boolean estado){
        this.estado=estado;
        this.nombre=nombre;
    }

    synchronized public void atender(String nombre){
    System.out.println(nombre+" Entrando a la "+this.nombre);
    //esperamos 5 seg
    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
     System.out.println(nombre+" saliendo de la "+this.nombre);
   }

   public boolean getEstado() {
    return estado;
   }
   public void setEstado(boolean e){
    estado=e;
   }

}
