// Clase HiloSacarDinero
class HiloSacarDinero extends Thread {
    private CuentaBancaria cuenta;
    private String nombre;
    private int cantidad;

    // Constructor de la clase
    HiloSacarDinero(CuentaBancaria micuenta, String nombre, int cantidad) {
        this.cuenta = micuenta;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    //añadimos synchronized para asegurar la atomicidad
    public void run() {
        synchronized(cuenta){
        cuenta.sacarDinero(nombre, cantidad);
        }
    }
}
