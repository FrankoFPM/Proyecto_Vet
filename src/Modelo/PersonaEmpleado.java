package Modelo;

public class PersonaEmpleado extends Persona {

    private String contraseña;
    private String cargo;
    private String nickname;

    public PersonaEmpleado() {
    }
    public PersonaEmpleado(String codigo, String nombre) {
        super(codigo, nombre);
    }
    
    public PersonaEmpleado(String contraseña, String cargo, String nickname, String codigo, String nombre, String apellido, String correo, int dni) {
        super(codigo, nombre, apellido, correo, dni);
        this.contraseña = contraseña;
        this.cargo = cargo;
        this.nickname = nickname;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
