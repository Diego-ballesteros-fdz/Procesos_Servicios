

public class Productor extends Thread {
    private Cola cola;
    private int n;

    //Constructor recibe la cola y un id para el hilo prodcutor
    public Productor(Cola c, int n) {
        cola = c;
        this.n = n;
    }

    public void run() {
        //System.out.println("productor ejecuta el run");
        for (int i = 1; i <= 10; i++) {
            try {
            //System.out.println("productor Esta en el bucle");
            int rand= (int)Math.round(Math.random()*10);
            System.out.println("Productor"+n+" añade: "+rand);
            cola.put(rand); //produce si es posible
                sleep(100);
            } catch (InterruptedException e) { 
                System.out.println("Error"+ e);
            }				
        }
        System.out.println("Productor ha acabado");
    }
}
