package Modelo;

public class PersonaCliente extends Persona {

    /*
     private String nombre;
    private String apellido;
    private int dni;
     */
    private String telefono;
    private String direccion;

    public PersonaCliente() {
    }

    public PersonaCliente(String codigo, String nombre) {
        super(codigo, nombre);
    }

    public PersonaCliente(String telefono, String correo, String direccion, String codigo, String nombre, String apellido, int dni) {
        super(codigo, nombre, apellido, correo, dni);
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
