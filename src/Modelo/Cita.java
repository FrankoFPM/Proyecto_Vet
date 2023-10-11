package Modelo;

import java.sql.Date;
import java.sql.Time;

public class Cita {

    private String codigo;
    private String codigoCliente;
    private String cliente;
    private String paciente;
    private String veterinario;
    private Date fecha;
    private Time hora;
    private String estado;

    public Cita() {
    }

    public Cita(String codigo, String codigoCliente, String cliente, String paciente, String veterinario, Date fecha, Time hora, String estado) {
        this.codigo = codigo;
        this.codigoCliente = codigoCliente;
        this.cliente = cliente;
        this.paciente = paciente;
        this.veterinario = veterinario;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

}
