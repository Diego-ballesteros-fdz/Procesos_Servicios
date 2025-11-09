
public class Supermercado {

    Caja cajas[]={
    new Caja("Caja1",false),
    new Caja("Caja2",false),
    new Caja("Caja3",false)
    }; //Array de boolean para saber si la caja esta libre o no

    /**
     * metodo de asignación de clientes
     */
   public void recibirCliente(int num,String nombre){

    cajas[num].setEstado(true);//ocupamos la caja
    cajas[num].atender(nombre);
    cajas[num].setEstado(false);//volvemos a liberar la caja

   }



   public int cajaLibre(){
    for(int i=0;i<cajas.length;i++){
        if(cajas[i].getEstado()==false){
            return i;
        }
    }
    return -1;//devolvemos -1 para indicar que no hay vaja libre
   }
    
}
