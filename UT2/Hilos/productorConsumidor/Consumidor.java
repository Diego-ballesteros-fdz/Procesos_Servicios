

public class Consumidor extends Thread {
 private Cola cola;
 private int id;
 private int valores[];//array de valores

 public Consumidor(Cola cola,int id){
    this.id=id;
    this.cola=cola;
    valores=new int[10];
    
 }

 public void run(){
    //System.out.println("Consumidor entra al run");
    int sum=0;
        for (int i = 0; i < 9; i++) {
            try{
            //System.out.println("Consumidor entra al bucle");
            valores[i] = cola.get(); //consume si es posible
            System.out.println("Consumidor"+id+" consume: "+valores[i]);
            //realizamos la logica solicitada
            sum+=valores[i];
            //simulamos el tiempo de proceso
            
                Thread.sleep(1000);
            }catch(Exception e){
                System.out.println("Error"+ e);
            }
        }
        System.out.println("El sumatorio consumido es: "+sum);
 }   
}
