
import java.util.concurrent.ArrayBlockingQueue;

public class Cola {
     private int numero;
    private ArrayBlockingQueue<Integer> queue; 

	public Cola (int capacidad){
		queue=new ArrayBlockingQueue<>(capacidad);
	}

	public Integer get() throws InterruptedException {
        return queue.take();
    }

	/*
     public synchronized int get() {
         // Queda a la espera hasta que la cola se llene ("mientras cola vacía espero en wait()")
    	  for(int i=0;i<disponible.length;i++){
            if(disponible[i]==true){
    	    try {
    	          wait();
    	    } catch (InterruptedException e) { }
        }
    	  }
    	  //Una vez hay valor disponible se devuelve
		  System.out.println("Se consume: " + numero);  
		  disponible = false;
    	  notify();
    	  return numero;
    	}
		  */

		public void put(int num) throws InterruptedException{
			queue.put(num);
		}


		  /*
    public synchronized void put(int valor) {
     // Queda a la espera hasta que la cola se vacíe ("mientras haya datos en la cola espero en wait()")
    	  while (disponible){
    	    try {
    	          wait();
    	    } catch (InterruptedException e) { }
    	  }
    	  numero = valor;
    	  disponible = true;
		  System.out.println("Se produce: " + numero);  
    	  notify();
    	}
		  */
    
}
