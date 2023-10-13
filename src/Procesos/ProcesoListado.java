package Procesos;

import DB.Conexion;
import Modelo.Paciente;
import Modelo.PersonaCliente;
import Modelo.PersonaEmpleado;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProcesoListado {

    public static String generarCodigo(String tabla, String col, String prefijo, int longitud) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_genCodigo;
        String codigo = "";

        try {
            String query = "{call sp_codigo_autogenerado(?,?,?,?,?)}";
            cs_genCodigo = cn.prepareCall(query);
            cs_genCodigo.setString(1, tabla);
            cs_genCodigo.setString(2, col);
            cs_genCodigo.setString(3, prefijo);
            cs_genCodigo.setInt(4, longitud);
            cs_genCodigo.registerOutParameter(5, Types.VARCHAR);
            cs_genCodigo.execute();
            codigo = cs_genCodigo.getString(5);
            cs_genCodigo.close();
        } catch (SQLException e) {
        }
        return codigo;
    }

    public static String generarCodigoUnico(String tabla, String col, String prefijo, int longitud) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_genCodigo;
        String codigo = "";

        try {
            String query = "{call sp_codigo_autogenerado_modificado(?,?,?,?,?)}";
            cs_genCodigo = cn.prepareCall(query);
            cs_genCodigo.setString(1, tabla);
            cs_genCodigo.setString(2, col);
            cs_genCodigo.setString(3, prefijo);
            cs_genCodigo.setInt(4, longitud);
            cs_genCodigo.registerOutParameter(5, Types.VARCHAR);
            cs_genCodigo.execute();
            codigo = cs_genCodigo.getString(5);
            cs_genCodigo.close();
        } catch (SQLException e) {
        }
        return codigo;
    }

    public static List<String[]> listarDatos(String tabla) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_listaClientes;

        List<String[]> datos = new ArrayList<>();

        try {
            String query = "{call sp_listarDatos(?)}";
            cs_listaClientes = cn.prepareCall(query);
            cs_listaClientes.setString(1, tabla);
            boolean resultado = cs_listaClientes.execute();
            if (resultado) {
                ResultSet rs = cs_listaClientes.getResultSet();

                while (rs.next()) {
                    String[] fila = new String[rs.getMetaData().getColumnCount()];
                    for (int i = 0; i < fila.length; i++) {
                        fila[i] = rs.getString(i + 1);
                    }
                    datos.add(fila);
                }
                rs.close();
            }
            cs_listaClientes.close();
        } catch (SQLException e) {
        }
        return datos;
    }

    public static List<String[]> listarCitasProximas() {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_listaClientes;

        List<String[]> datos = new ArrayList<>();

        try {
            String query = "{call sp_seleccionar_citas_programadas()}";
            cs_listaClientes = cn.prepareCall(query);
            boolean resultado = cs_listaClientes.execute();
            if (resultado) {
                ResultSet rs = cs_listaClientes.getResultSet();

                while (rs.next()) {
                    String[] fila = new String[rs.getMetaData().getColumnCount()];
                    for (int i = 0; i < fila.length; i++) {
                        fila[i] = rs.getString(i + 1);
                    }
                    datos.add(fila);
                }
                rs.close();
            }
            cs_listaClientes.close();
        } catch (SQLException e) {
        }
        return datos;
    }

    public static int contarClientes() {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_listaClientes;

        int numClientes = 0;

        try {
            String query = "{CALL sp_contar_clientes()}";
            cs_listaClientes = cn.prepareCall(query);
            boolean resultado = cs_listaClientes.execute();
            if (resultado) {
                ResultSet rs = cs_listaClientes.getResultSet();
                if (rs.next()) {
                    numClientes = rs.getInt(1);
                }
                rs.close();
            }
            cs_listaClientes.close();
        } catch (SQLException e) {
        }
        return numClientes;
    }
    public static int contarCitasProgramadas() {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_listaCitas;

        int numCitas = 0;

        try {
            String query = "{CALL sp_contar_citas_programadas()}";
            cs_listaCitas = cn.prepareCall(query);
            boolean resultado = cs_listaCitas.execute();
            if (resultado) {
                ResultSet rs = cs_listaCitas.getResultSet();
                if (rs.next()) {
                    numCitas = rs.getInt(1);
                }
                rs.close();
            }
            cs_listaCitas.close();
        } catch (SQLException e) {
        }
        return numCitas;
    }

    //Obtener clientes para el CBox
    public static ArrayList<PersonaCliente> obtenerClientes() {

        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();

        ArrayList<PersonaCliente> listadoClientes = new ArrayList<>();
        PersonaCliente cliente;
        CallableStatement cs_listadoClientes;
        ResultSet rs;

        try {
            cs_listadoClientes = cn.prepareCall("{call sp_listar_clientes}");
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

    public static ArrayList<Paciente> obtenerPacientes(String codigo) {

        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();

        ArrayList<Paciente> listadoPacientes = new ArrayList<>();
        Paciente paciente;
        CallableStatement cs_listadoPacientes;
        ResultSet rs;

        try {
            cs_listadoPacientes = cn.prepareCall("{call sp_listar_pacientes(?)}");
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

    public static ArrayList<PersonaEmpleado> obtenerVeterinarios() {

        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();

        ArrayList<PersonaEmpleado> listadoEmpleados = new ArrayList<>();
        PersonaEmpleado empelado;
        CallableStatement cs_listadoEmpleados;
        ResultSet rs;

        try {
            cs_listadoEmpleados = cn.prepareCall("{call sp_listar_veterinarios}");
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

    public static void tituloTabla(JTable tabla, String[] titulos) {
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        tabla.setModel(modelo);
    }

    public static void llenarTabla(JTable tabla, List<String[]> datos) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
    }

    //Filtrar comboClientes
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

    public static String normalize(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[^\\p{ASCII}]", ""); // quitar caracteres no ASCII
        return normalized.toLowerCase(); // convertir a minúsculas
    }
    
    
    public static ArrayList<Paciente> obtenerTodoPacientes() {

        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();

        ArrayList<Paciente> listadoPacientes = new ArrayList<>();
        Paciente paciente;
        CallableStatement cs_listadoPacientes;
        ResultSet rs;

        try {
            cs_listadoPacientes = cn.prepareCall("{call sp_listar_todo_pacientes}");
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
}
