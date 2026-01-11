
public class Factura {
    private String numFact;
    private String fecha;
    private double importe;
    private String tipoIva;
    private double iva;
    private double importeTotal;

    public Factura(String numFact,String fecha,double importe,String tipoIva){
        this.numFact=numFact;
        this.fecha=fecha;
        this.importe=importe;
        this.tipoIva=tipoIva;
    }

    public double calcularImporteTotal(){
        //Calcular el IVA
        iva=calcularIva();
        //calcular total
        return importe+iva;
    }

    public double calcularIva(){
        switch (tipoIva) {
            case "IGC":
                //7%
                return importe*0.07;
            case "ESP":
                //21%
                return importe*0.21;
            case "EUR":
                //15
                return importe*0.15;        
            default:
                //algo fallo
                System.out.println("Tipo de iva no existe, devolviendo -1");
                return -1;
        }
    }

    public String toString(){
        return "Datos de la factura("+numFact+"): "+"\n"+
                "\t Fecha: "+fecha+".\n"+
                "\t Importe: "+importe+".\n"+
                "\t Tipo IVA: "+tipoIva+".\n"+
                "\t IVA: "+iva+" \n"+
                "\t Importe total: "+importeTotal+".\n";
    }
    
}
