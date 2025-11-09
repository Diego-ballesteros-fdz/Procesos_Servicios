// Clase HiloIngresarDinero
class HiloIngresarDinero extends Thread {
    private CuentaBancaria cuenta;
    private String nombre;
    private int cantidad;

    // Constructor de la clase
    HiloIngresarDinero(CuentaBancaria micuenta, String nombre, int cantidad) {
        this.cuenta=micuenta;
        this.nombre=nombre;
        this.cantidad=cantidad;
    }

    //añadimos synchronized para asegurar la atomicidad
    public void run() {
        synchronized(cuenta){
        cuenta.ingresarDinero(nombre, cantidad);
        }
    }
}