package Modelo;

import java.sql.Date;

public class Producto {

    private String codigo;
    private String nombre;
    private String marca;
    private double precio;
    private int cantidad;
    private String categoria;
    private Date fecha;

    private String info;

    public Producto() {
    }

    public Producto(String codigo, String info) {
        this.codigo = codigo;
        this.info = info;
    }
    public Producto(String codigo, String info,String marca) {
        this.codigo = codigo;
        this.info = info;
        this.marca = marca;
    }

    public Producto(String codigo, String nombre, String marca, double precio, int cantidad, String categoria, Date fecha) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return getInfo();
    }

}
