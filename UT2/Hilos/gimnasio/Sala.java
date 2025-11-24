

public class Sala {
    int aforo;

    public Sala(){
        aforo=0;
    }
    
     synchronized boolean libre(){
        try{
            boolean atendido=false;
            do{
                if(aforo<5){
                    return true;
                }else{
                    wait();
                }
            }while(!atendido);
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    synchronized void salir(){
        aforo--;
        notify();
    }

    /**
     * metodo que simula el entrenamiento
     */
    public void entrenar(String nombre){
        aforo++;
        System.out.println(nombre+" entrando a entrenar");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println(nombre+" saliendo de entrenar");
    }



}
