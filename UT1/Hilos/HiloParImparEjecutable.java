
public class HiloParImparEjecutable {
    public static void main(String[] args) throws InterruptedException {
        Thread h1=new Thread(new HiloParImpar(1),"HILO 1");
        Thread h2=new Thread(new HiloParImpar(2),"HILO 2");
        h2.start();
        h2.join();
        h1.start();
        //System.out.println("Finaliza el programa");

    }
    
}
