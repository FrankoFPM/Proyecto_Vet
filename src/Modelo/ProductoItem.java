package Modelo;

public class ProductoItem extends Producto {

    private String codigoBoleta;

    public ProductoItem() {
    }

    public ProductoItem(String codigoBoleta, String codigo, String nombre, double precio, int cantidad) {
        super(codigo, nombre, precio, cantidad);
        this.codigoBoleta = codigoBoleta;
    }

    public String getCodigoBoleta() {
        return codigoBoleta;
    }

    public void setCodigoBoleta(String codigoBoleta) {
        this.codigoBoleta = codigoBoleta;
    }
    
    

}
