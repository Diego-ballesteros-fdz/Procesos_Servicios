
public class HiloParImpar implements Runnable{
    private int tipo;
    public void run(){
        do{
        tipo++;
    if(tipo%2==0){//filtramos por par
        if (Thread.currentThread().getName()=="HILO xx" && tipo<=100){//filtramos por hilo xx
                System.out.println(Thread.currentThread().getName()+" generando número par"+tipo);
        }
    }else{//filtramos por impar
        if (Thread.currentThread().getName()=="HILO yy" && tipo>100){//filtramos por hilo yy
                System.out.println(Thread.currentThread().getName()+" generando número impar"+tipo);
        }
    }
    }while(tipo!=200);
    }

}