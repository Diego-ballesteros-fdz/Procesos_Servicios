

public class Consumidor2 extends Thread {
 private Cola cola;
 private int id;
 private int valor;

 public Consumidor2(Cola cola,int id){
    this.id=id;
    this.cola=cola;
    valor=0;
    
 }

 public void run(){
    //System.out.println("Consumidor entra al run");
    int producto=1;
        for (int i = 1; i <= 5; i++) {
            try{
                //System.out.println("Consumidor entra al bucle");
                valor = cola.get(); //consume si es posible
                System.out.println("Consumidor"+id+" consume: "+valor);
                //realizamos la logica solicitada
                producto*=valor;
                //simulamos el tiempo de proceso
                Thread.sleep(100);
            }catch(Exception e){
                System.out.println("Error"+ e);
            }
        }
        System.out.println("El producto obtenido es: "+producto);
 }   
}
