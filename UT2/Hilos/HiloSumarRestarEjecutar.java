public class HiloSumarRestarEjecutar {
    public static void main(String[] args) throws InterruptedException {
        Thread h1=new Thread(new HiloSumasRestas(100,"+"),"HiloSumar1");
        Thread h2=new Thread(new HiloSumasRestas(100,"-"),"HiloRestar2");
        Thread h3=new Thread(new HiloSumasRestas(1,"+"),"HiloSumar3");
        Thread h4=new Thread(new HiloSumasRestas(1,"-"),"HiloRestar4");

        //para que funcione como queremos debemos omitir el uso del ultimo hilo y gestiar la ejecución

        h4.start();
        h4.join();
        h3.start();
        h3.join();
        h2.start();
    }
}
