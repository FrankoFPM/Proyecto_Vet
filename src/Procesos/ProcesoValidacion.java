package Procesos;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ProcesoValidacion {

    public static void validarInputs(JTextField[] inputs, String[] nombre) {
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El campo " + nombre[i] + " no puede estar vacío");
            }
        }
    }
    public static void placeholderJtxt(JTextField[] inputs, String[] nombre){
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].putClientProperty("JTextField.placeholderText", "Ingresar " + nombre[i]);
        }
    }
}
