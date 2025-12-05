

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
        do{
            try{
                //System.out.println("Consumidor entra al bucle");
                valor = cola.get(); //consume si es posible
                System.out.println("Consumidor"+id+" consume: "+valor);
                //realizamos la logica solicitada
                if(valor%2==0){
                    sum+=valor;
                }else{
                    cola.put(valor);
                }
                
                //simulamos el tiempo de proceso
                Thread.sleep(1000);
            }catch(Exception e){
                System.out.println("Error"+ e);
            }
        }while(valor!=-1);
        System.out.println("El sumatorio consumido es: "+sum);
 }   
}
