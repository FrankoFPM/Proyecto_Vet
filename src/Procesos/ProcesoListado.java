package Procesos;

import DB.Conexion;
import Modelo.PersonaCliente;
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
    
    public static String normalize(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[^\\p{ASCII}]", ""); // quitar caracteres no ASCII
        return normalized.toLowerCase(); // convertir a minúsculas
    }
}
