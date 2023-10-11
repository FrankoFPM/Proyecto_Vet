package Modelo;

public class Persona {

    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private int dni;

    public Persona() {
    }

    public Persona(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Persona(String codigo, String nombre, String apellido, String correo, int dni) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.dni = dni;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return getNombre();
    }

}
