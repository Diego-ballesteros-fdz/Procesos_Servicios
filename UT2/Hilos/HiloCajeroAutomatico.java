// Clase Principal
public class HiloCajeroAutomatico {
    public static void main(String[] args) {
        // Creamos la cuenta
        CuentaBancaria c=new CuentaBancaria();
        //creamos lo hilos
        //ingresan
        HiloIngresarDinero h1= new HiloIngresarDinero(c, "Padre", 200);
        HiloIngresarDinero h2= new HiloIngresarDinero(c, "Hijo1", 300);
        //sacan
        HiloSacarDinero h3= new HiloSacarDinero(c, "Madre", 800);
        HiloSacarDinero h4= new HiloSacarDinero(c, "Hijo2", 800);
        HiloSacarDinero h5= new HiloSacarDinero(c, "Abuelo", 600);

        //ejecutamos los hilos
        h1.start();
        h3.start();
        h2.start();
        h4.start();
        h5.start();
    }
}
