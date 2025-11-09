// Clase CuentaBancaria
// Definición de la cuenta con un saldo inicial y de las operaciones para
//ingresar dinero (método ingresarDinero) y sacar dinero (método sacarDinero)de // la cuenta
public class CuentaBancaria {
    // Saldo inicial de la cuenta
    int saldo = 1000;

    void restar(int cantidad) { saldo=saldo-cantidad;}
    
    void sumar(int cantidad) { saldo=saldo+cantidad;}

    // método sacarDinero:
    // nombre -> persona que realiza la operación
    // importe -> cantidad a retirar
    void sacarDinero(String nombre, int importe) {
        //synchronized (System.out){
        if (saldo >= importe) {
			System.out.println(nombre+": RETIRA "+importe+" (ACTUAL ES: "+saldo+ ")" );
            System.out.println("SALDO ACTUALIZADO: "+(saldo-importe));
            restar(importe);
            
        }else {
			System.out.println(nombre+ " No puede retirar "+importe+", NO HAY SALDO("+saldo+")" );
		}
		if (saldo < 0) {
			System.out.println("SALDO NEGATIVO => "+saldo);
		}
        // Después de la operación dormir el hilo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // método ingresarDinero
    // nombre -> persona que realiza la operación
    // importe -> cantidad a retirar
    void ingresarDinero(String nombre, int importe) {
        System.out.println(nombre+": INGRESA "+importe+" (ACTUAL ES: "+saldo+ ")" );
        System.out.println("SALDO ACTUALIZADO: "+(saldo+importe));
        sumar(importe);
        // Después de la operación dormir el hilo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}