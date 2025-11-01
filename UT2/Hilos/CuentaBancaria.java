// Clase CuentaBancaria
// Definición de la cuenta con un saldo inicial y de las operaciones para
//ingresar dinero (método ingresarDinero) y sacar dinero (método sacarDinero)de // la cuenta
class CuentaBancaria {
//Saldo inicial de la cuenta
int saldo = 1000;
//método sacarDinero:
// nombre -> persona que realiza la operación
//importe -> cantidad a retirar
void sacarDinero (String nombre, int importe)
{
//Después de la operación dormir el hilo
try {
Thread.sleep(1000);
}
catch (InterruptedException e) {
e.printStackTrace();
}
}
//método ingresarDinero
//nombre -> persona que realiza la operación
//importe -> cantidad a retirar
void ingresarDinero (String nombre, int importe)
{
//Después de la operación dormir el hilo
try {
Thread.sleep(1000);
}
catch (InterruptedException e) {
e.printStackTrace();
}
}
}