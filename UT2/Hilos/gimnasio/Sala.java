

public class Sala {
    boolean estado;//esta libre,true o ocupada false
    String id;

    public Sala(int id){
        estado=true;//la iniciamos libre
        this.id="Sala "+id;
    }

    /**
     * metodo que simula el entrenamiento
     */
    synchronized void entrenar(String nombre){
        System.out.println(nombre+" entrando a entrenar a la "+id);
        try{
            Thread.sleep(5000);
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println(nombre+" saliendo de entrenar de la "+id);
    }

}
