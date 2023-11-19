package DAO;

import java.text.Normalizer;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Modelo.Paciente;
import Modelo.PersonaCliente;
import Modelo.PersonaEmpleado;
import Modelo.ProductoInventario;
import Procesos.ProcesoListado;

public class CargarCombos {

    /**
     * Filtra los elementos de un JComboBox en función del texto ingresado.
     * Si el texto ingresado está vacío, se muestran todos los elementos.
     * De lo contrario, solo se muestran los elementos que contienen el texto
     * ingresado.
     *
     * @param enteredText El texto ingresado por el usuario.
     * @param comboBox    El JComboBox a filtrar.
     */
    public static void filterComboBox(String enteredText, JComboBox<PersonaCliente> comboBox) {
        if (!comboBox.isPopupVisible()) {
            comboBox.showPopup();
        }

        ArrayList<PersonaCliente> filterArray = new ArrayList<>();

        if (enteredText.isEmpty()) {
            filterArray = new ArrayList<>(ProcesoListado.obtenerClientes());
        } else {
            String normalizedEnteredText = normalize(enteredText);

            for (PersonaCliente item : ProcesoListado.obtenerClientes()) {
                String normalizedItem = normalize(item.toString());
                if (normalizedItem.contains(normalizedEnteredText)) {
                    filterArray.add(item);
                }
            }
        }

        DefaultComboBoxModel<PersonaCliente> model = (DefaultComboBoxModel<PersonaCliente>) comboBox.getModel();
        model.removeAllElements();
        for (PersonaCliente s : filterArray) {
            model.addElement(s);
        }

        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        textField.setText(enteredText);
    }

    public static void filterComboBoxPacientes(String enteredText, JComboBox<Paciente> comboBox, String codigo) {
        if (!comboBox.isPopupVisible()) {
            comboBox.showPopup();
        }

        ArrayList<Paciente> filterArray = new ArrayList<>();

        if (enteredText.isEmpty()) {
            filterArray = new ArrayList<>(ProcesoListado.obtenerPacientes(codigo));
        } else {
            String normalizedEnteredText = normalize(enteredText);

            for (Paciente item : ProcesoListado.obtenerPacientes(codigo)) {
                String normalizedItem = normalize(item.toString());
                if (normalizedItem.contains(normalizedEnteredText)) {
                    filterArray.add(item);
                }
            }
        }

        DefaultComboBoxModel<Paciente> model = (DefaultComboBoxModel<Paciente>) comboBox.getModel();
        model.removeAllElements();
        for (Paciente s : filterArray) {
            model.addElement(s);
        }

        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        textField.setText(enteredText);
    }

    public static void filterComboBoxAllPacientes(String enteredText, JComboBox<Paciente> comboBox) {
        if (!comboBox.isPopupVisible()) {
            comboBox.showPopup();
        }

        ArrayList<Paciente> filterArray = new ArrayList<>();

        if (enteredText.isEmpty()) {
            filterArray = new ArrayList<>(ProcesoListado.obtenerTodoPacientes());
        } else {
            String normalizedEnteredText = normalize(enteredText);

            for (Paciente item : ProcesoListado.obtenerTodoPacientes()) {
                String normalizedItem = normalize(item.toString());
                if (normalizedItem.contains(normalizedEnteredText)) {
                    filterArray.add(item);
                }
            }
        }

        DefaultComboBoxModel<Paciente> model = (DefaultComboBoxModel<Paciente>) comboBox.getModel();
        model.removeAllElements();
        for (Paciente s : filterArray) {
            model.addElement(s);
        }

        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        textField.setText(enteredText);
    }

    public static void filterComboBoxVeterinarios(String enteredText, JComboBox<PersonaEmpleado> comboBox) {
        if (!comboBox.isPopupVisible()) {
            comboBox.showPopup();
        }

        ArrayList<PersonaEmpleado> filterArray = new ArrayList<>();

        if (enteredText.isEmpty()) {
            filterArray = new ArrayList<>(ProcesoListado.obtenerVeterinarios());
        } else {
            String normalizedEnteredText = normalize(enteredText);

            for (PersonaEmpleado item : ProcesoListado.obtenerVeterinarios()) {
                String normalizedItem = normalize(item.toString());
                if (normalizedItem.contains(normalizedEnteredText)) {
                    filterArray.add(item);
                }
            }
        }

        DefaultComboBoxModel<PersonaEmpleado> model = (DefaultComboBoxModel<PersonaEmpleado>) comboBox.getModel();
        model.removeAllElements();
        for (PersonaEmpleado s : filterArray) {
            model.addElement(s);
        }

        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        textField.setText(enteredText);
    }

    public static void filterComboBoxProductos(String enteredText, JComboBox<ProductoInventario> comboBox) {
        if (!comboBox.isPopupVisible()) {
            comboBox.showPopup();
        }

        ArrayList<ProductoInventario> filterArray = new ArrayList<>();

        if (enteredText.isEmpty()) {
            filterArray = new ArrayList<>(ProcesoListado.obtenerProducto());
        } else {
            String normalizedEnteredText = normalize(enteredText);

            for (ProductoInventario item : ProcesoListado.obtenerProducto()) {
                String normalizedItem = normalize(item.toString());
                if (normalizedItem.contains(normalizedEnteredText)) {
                    filterArray.add(item);
                }
            }
        }

        DefaultComboBoxModel<ProductoInventario> model = (DefaultComboBoxModel<ProductoInventario>) comboBox.getModel();
        model.removeAllElements();
        for (ProductoInventario s : filterArray) {
            model.addElement(s);
        }

        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        textField.setText(enteredText);
    }

    /**
     * Normalizes a given string by removing non-ASCII characters and converting it
     * to lowercase.
     * 
     * @param str the string to be normalized
     * @return the normalized string
     */
    public static String normalize(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[^\\p{ASCII}]", ""); // quitar caracteres no ASCII
        return normalized.toLowerCase(); // convertir a minúsculas
    }
}
