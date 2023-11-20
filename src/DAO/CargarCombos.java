package DAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class CargarCombos extends ConexionDB {

    public ArrayList<PersonaCliente> obtenerClientes() {

        ArrayList<PersonaCliente> listadoClientes = new ArrayList<>();
        PersonaCliente cliente;
        CallableStatement cs_listadoClientes;
        ResultSet rs;

        try {
            cs_listadoClientes = conectar.prepareCall("{call sp_listar_clientes}");
            rs = cs_listadoClientes.executeQuery();
            cliente = new PersonaCliente();
            cliente.setCodigo("0");
            cliente.setNombre("[Selecciona una opción...]");
            listadoClientes.add(cliente);
            while (rs.next()) {
                cliente = new PersonaCliente();
                cliente.setCodigo(rs.getString(1));
                cliente.setNombre(rs.getString(2));
                listadoClientes.add(cliente);
            }

        } catch (SQLException e) {
            e.toString();
        }
        return listadoClientes;
    }

    public ArrayList<Paciente> obtenerPacientes(String codigo) {

        ArrayList<Paciente> listadoPacientes = new ArrayList<>();
        Paciente paciente;
        CallableStatement cs_listadoPacientes;
        ResultSet rs;

        try {
            cs_listadoPacientes = conectar.prepareCall("{call sp_listar_pacientes(?)}");
            cs_listadoPacientes.setString(1, codigo);
            rs = cs_listadoPacientes.executeQuery();
            paciente = new Paciente();
            paciente.setCodigo("0");
            paciente.setNombre("[Selecciona una opción...]");
            listadoPacientes.add(paciente);
            while (rs.next()) {
                paciente = new Paciente();
                paciente.setCodigo(rs.getString(1));
                paciente.setNombre(rs.getString(2));
                listadoPacientes.add(paciente);
            }

        } catch (SQLException e) {
            e.toString();
        }
        return listadoPacientes;
    }

    public ArrayList<PersonaEmpleado> obtenerVeterinarios() {

        ArrayList<PersonaEmpleado> listadoEmpleados = new ArrayList<>();
        PersonaEmpleado empelado;
        CallableStatement cs_listadoEmpleados;
        ResultSet rs;

        try {
            cs_listadoEmpleados = conectar.prepareCall("{call sp_listar_veterinarios}");
            rs = cs_listadoEmpleados.executeQuery();
            empelado = new PersonaEmpleado();
            empelado.setCodigo("0");
            empelado.setNombre("[Selecciona una opción...]");
            listadoEmpleados.add(empelado);
            while (rs.next()) {
                empelado = new PersonaEmpleado();
                empelado.setCodigo(rs.getString(1));
                empelado.setNombre(rs.getString(2));
                listadoEmpleados.add(empelado);
            }

        } catch (SQLException e) {
            e.toString();
        }
        return listadoEmpleados;
    }

    public ArrayList<ProductoInventario> obtenerProducto() {

        ArrayList<ProductoInventario> listadoProductos = new ArrayList<>();
        ProductoInventario producto;
        CallableStatement cs_listadoProductos;
        ResultSet rs;

        try {
            cs_listadoProductos = conectar.prepareCall("{call sp_listar_productos}");
            rs = cs_listadoProductos.executeQuery();
            producto = new ProductoInventario();
            producto.setCodigo("0");
            producto.setNombre("[Selecciona una opción...]");
            producto.setMarca("none");
            producto.setPrecio(0);
            producto.setInfo("[Selecciona una opción...]");
            listadoProductos.add(producto);
            while (rs.next()) {
                producto = new ProductoInventario();
                producto.setCodigo(rs.getString(1));
                producto.setNombre(rs.getString(2));
                producto.setMarca(rs.getString(3));
                producto.setPrecio(rs.getDouble(4));
                producto.setInfo(rs.getString(5));
                listadoProductos.add(producto);
            }

        } catch (SQLException e) {
            e.toString();
        }
        return listadoProductos;
    }

    public ArrayList<ProductoInventario> obtenerServicios() {

        ArrayList<ProductoInventario> listadoProductos = new ArrayList<>();
        ProductoInventario producto;
        CallableStatement cs_listadoServicios;
        ResultSet rs;

        try {
            cs_listadoServicios = conectar.prepareCall("{call sp_listar_servicios}");
            rs = cs_listadoServicios.executeQuery();
            producto = new ProductoInventario();
            producto.setCodigo("0");
            producto.setNombre("[Selecciona una opción...]");
            producto.setMarca("none");
            producto.setPrecio(0);
            producto.setInfo("[Selecciona una opción...]");
            listadoProductos.add(producto);
            while (rs.next()) {
                producto = new ProductoInventario();
                producto.setCodigo(rs.getString(1));
                producto.setNombre(rs.getString(2));
                producto.setMarca(rs.getString(3));
                producto.setPrecio(rs.getDouble(4));
                producto.setInfo(rs.getString(2));
                listadoProductos.add(producto);
            }

        } catch (SQLException e) {
            e.toString();
        }
        return listadoProductos;
    }

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
