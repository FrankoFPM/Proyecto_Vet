package Modelo;

import java.text.DecimalFormat;

public class PersonaCliente extends Persona {

    /*
     private String nombre;
    private String apellido;
    private int dni;
     */
    String telefono;
    String correo;
    String direccion;
    static int contador=0;
    
    public PersonaCliente() {
    }

    public PersonaCliente(String telefono, String correo, String direccion, String nombre, String apellido, int dni) {
        super(nombre, apellido, dni);
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        
        contador++;//incrementamos el contador
        DecimalFormat df = new DecimalFormat("CLI-0000");
        super.setCodigo(df.format(contador));
    }

}
