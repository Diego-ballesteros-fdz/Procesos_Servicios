
public class HiloSumasRestas implements Runnable{
    private int numero=1000;
    private String operacion;
    private int numveces;
    
    //Constructor de la clase
    public HiloSumasRestas (int numveces, String operacion){
        this.operacion=operacion;
        this.numveces=numveces;
    }
    //Incrementará numero el numveces indicado
    public int incrementar (int numveces){
        return this.numero+=numveces;
    }
    //Decrementará numero el numvece indicado
    public int decrementar (int numveces){
        return this.numero-=numveces;
    }
    public void run() {
   
    switch(operacion){
        case "+": //Si la operación es “+” se invocará al método incrementar
            incrementar(numveces);
            System.out.println(Thread.currentThread().getName()+" Ha modificado el numero a "+numero);
            break;
        case "-"://Si la operación es “-” se invocará al método decrementar
            decrementar(numveces);
            System.out.println(Thread.currentThread().getName()+" Ha modificado el numero a "+numero);
            break;
        
        }
    }
}
