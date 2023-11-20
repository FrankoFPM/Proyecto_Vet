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

    // Método para validar el correo electrónico
    public static boolean validarEmail(String correo) {
        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Correo electrónico inválido");
            return false;
        }
        return true;
    }

    // Método para validar el número de teléfono
    public static boolean validarTelefono(String telefono) {
        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Número de teléfono inválido");
            return false;
        }
        return true;
    }

    // Método para validar combobox
    public static boolean validarCombobox(JComboBox combo, String nombre) {
        if (combo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una opcion de la lista " + nombre + " ╰（‵□′）╯", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Método para validar el DNI
    public static boolean validarDni(int dni) {
        if (String.valueOf(dni).length() != 8) {
            JOptionPane.showMessageDialog(null, "DNI inválido");
            return false;
        }
        return true;
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

    public static void placeholderJtxt(JTextField[] inputs, String[] nombre) {
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].putClientProperty("JTextField.placeholderText", "Ingresar " + nombre[i]);
        }
    }

}
