package ejercicios;
public class HiloParImpar implements Runnable{
    private int tipo;

    public HiloParImpar(int tipo){
        this.tipo=tipo;
    }
    public void run(){
        int num=0;
        do{
            num++;
    if(tipo==1){//filtramos por par
        if (Thread.currentThread().getName()=="HILO 1" && num<=100){//filtramos por hilo 1
                System.out.println(Thread.currentThread().getName()+" generando número par"+num);
        }
    }else{//filtramos por impar
        if (Thread.currentThread().getName()=="HILO 2" && num>100){//filtramos por hilo 2   
                System.out.println(Thread.currentThread().getName()+" generando número impar"+num);
        }
    }
    }while(num!=200);
    }

}