package Modelo;

import java.sql.Date;
import java.sql.Time;

public class Reporte {

    private String codigo;
    private String codigo_entidad;
    private String entidad;
    private Date fecha;
    private Time hora;

    public Reporte() {
    }

    public Reporte(String codigo, String codigo_entidad, String entidad, Date fecha, Time hora) {
        this.codigo = codigo;
        this.codigo_entidad = codigo_entidad;
        this.entidad = entidad;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo_entidad() {
        return codigo_entidad;
    }

    public void setCodigo_entidad(String codigo_entidad) {
        this.codigo_entidad = codigo_entidad;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
