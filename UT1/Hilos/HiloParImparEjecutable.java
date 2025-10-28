
public class HiloParImparEjecutable {
    public static void main(String[] args) {
        new Thread(new HiloParImpar(),"HILO xx").start();
        new Thread(new HiloParImpar(),"HILO yy").start();
    }
    
}
