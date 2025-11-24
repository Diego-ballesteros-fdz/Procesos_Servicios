
import java.util.LinkedList;


public class Cola {
     private int numero;
	 LinkedList<Integer> queue;
    

	public Cola (int capacidad){
		queue=new LinkedList<Integer>();
	}

	synchronized public int get() throws InterruptedException {
		while(queue.isEmpty()){ //cola vacia hay que esperar
			wait();
		}

        int i= queue.getFirst(); // almacena
		queue.removeFirst(); //elimina 
		notifyAll();
		return i; //devuelve
		

    }


	synchronized public void put(int num) throws InterruptedException{
		while(queue.size()==4){ //cola llena
			wait();
		}
		queue.addLast(num);
		notifyAll();
	}
}
