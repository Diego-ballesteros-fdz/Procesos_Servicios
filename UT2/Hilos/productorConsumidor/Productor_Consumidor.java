

public class Productor_Consumidor {
  public static void main(String[] args) {  

    System.out.println("Empezamos con productor consumidor");
    
    Cola cola = new Cola(5);
	
    Productor prod = new Productor(cola, 1);	
	  Consumidor cons = new Consumidor(cola, 1);
    Consumidor2 cons2=new Consumidor2(cola, 2);
    System.out.println("Se crearon los hilos");
	
    prod.start();
	  cons.start();
    cons2.start();
    System.out.println("Los hilos estan ejecutandose");
    try{
    prod.join();
    prod.join();
    }catch(Exception e){
        System.out.println("Error"+ e);
    }
  }
}
