package Modelo;

public class Producto {

    private String codigo;
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto() {
    }

    public Producto(String codigo, double precio) {
        this.codigo = codigo;
        this.precio = precio;
    }   

    public Producto(String codigo, String nombre, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
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
    
    //metodos
    public double total(){
        return getCantidad() * getPrecio();
    }

}
