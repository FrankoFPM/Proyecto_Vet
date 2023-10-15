package Modelo;

import java.sql.Date;

public class ProductoInventario extends Producto{

//    private String codigo;
//    private String nombre;
    private String marca;
//    private double precio;
//    private int cantidad;
    private String categoria;
    private Date fecha;

    private String info;

    public ProductoInventario() {
    }

//    public ProductoInventario(String codigo, String info) {
//        this.codigo = codigo;
//        this.info = info;
//    }
    public ProductoInventario(String codigo, String info,double precio) {
        super(codigo, precio);
        this.info = info;
    }

    public ProductoInventario(String codigo, String nombre, String marca, double precio, int cantidad, String categoria, Date fecha) {
        super(codigo, nombre, precio, cantidad);
        this.marca = marca;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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
    
    //metodos
    /*
    public double total(){
        return getCantidad() * getPrecio();
    }
*/
}
