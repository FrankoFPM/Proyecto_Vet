package Modelo;

public class PersonaCliente extends Persona {

    /*
     private String nombre;
    private String apellido;
    private int dni;
     */
    private String telefono;
    private String correo;
    private String direccion;

    public PersonaCliente() {
    }

    public PersonaCliente(String telefono, String correo, String direccion, String nombre, String apellido, int dni) {
        super(nombre, apellido, dni);
        this.telefono = telefono;
        this.correo = correo;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
