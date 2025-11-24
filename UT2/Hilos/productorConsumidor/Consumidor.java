

public class Consumidor extends Thread {
 private Cola cola;
 private int id;
 private int valor;

 public Consumidor(Cola cola,int id){
    this.id=id;
    this.cola=cola;
    valor=0;
    
 }

 public void run(){
    //System.out.println("Consumidor entra al run");
    int sum=0;
        for (int i = 1; i <= 10; i++) {
            try{
                //System.out.println("Consumidor entra al bucle");
                valor = cola.get(); //consume si es posible
                System.out.println("Consumidor"+id+" consume: "+valor);
                //realizamos la logica solicitada
                sum+=valor;
                //simulamos el tiempo de proceso
                Thread.sleep(1000);
            }catch(Exception e){
                System.out.println("Error"+ e);
            }
        }
        System.out.println("El sumatorio consumido es: "+sum);
 }   
}
