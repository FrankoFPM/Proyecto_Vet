package Modelo;

import java.sql.Date;
import java.sql.Time;

public class ReporteClinico extends Reporte {

    private String causa;
    private String pronostico;
    private String sintomas;
    private String tratamiento;

    public ReporteClinico() {
    }    

    public ReporteClinico(String causa, String pronostico, String sintomas, String tratamiento, String codigo, String codigo_entidad, String entidad, Date fecha, Time hora) {
        super(codigo, codigo_entidad, entidad, fecha, hora);
        this.causa = causa;
        this.pronostico = pronostico;
        this.sintomas = sintomas;
        this.tratamiento = tratamiento;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getPronostico() {
        return pronostico;
    }

    public void setPronostico(String pronostico) {
        this.pronostico = pronostico;
    }

}
