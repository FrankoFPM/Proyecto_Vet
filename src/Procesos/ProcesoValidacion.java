package Procesos;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ProcesoValidacion {

    public static boolean validarInputs(JTextField[] inputs, String[] nombre) {
        boolean pass = true;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El campo " + nombre[i] + " no puede estar vacío");
                pass = false;
            }
        }
        return pass;
    }
    public static boolean validarCombos(JComboBox[] inputs, String[] nombre) {
        boolean pass = true;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione una opcion de la lista " + nombre[i] + " ╰（‵□′）╯");
                pass = false;
            }
        }
        return pass;
    }
    public static void placeholderJtxt(JTextField[] inputs, String[] nombre){
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].putClientProperty("JTextField.placeholderText", "Ingresar " + nombre[i]);
        }
    }
    
    
}
