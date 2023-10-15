package Modelo;

import java.sql.Date;
import java.sql.Time;

public class ReporteVenta extends Reporte {
    
    private int cantidaditems;
    private double importe;
    static final double IGV=0.18;

    public ReporteVenta(int cantidaditems, double importe, String codigo, String codigo_entidad, String entidad, Date fecha, Time hora) {
        super(codigo, codigo_entidad, entidad, fecha, hora);
        this.cantidaditems = cantidaditems;
        this.importe = importe;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getCantidaditems() {
        return cantidaditems;
    }

    public void setCantidaditems(int cantidaditems) {
        this.cantidaditems = cantidaditems;
    }
    
    //Metodos
    public double impuesto(){
        return getImporte() * IGV;
    }
    public double importeFinal(){
        return getImporte()-impuesto();
    }
}
