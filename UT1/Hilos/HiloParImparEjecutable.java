
public class HiloParImparEjecutable {
    public static void main(String[] args) {
        new Thread(new HiloParImpar(1),"HILO 1").start();
        new Thread(new HiloParImpar(2),"HILO 2").start();
    }
    
}
