package DAO;

import DB.Conexion;
import Modelo.ProductoInventario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MetodosList {

    /**
     * Genera un código utilizando la tabla, columna, prefijo y longitud
     * especificados.
     * 
     * @param tabla    el nombre de la tabla
     * @param col      el nombre de la columna
     * @param prefijo  el prefijo que se agregará al código generado
     * @param longitud la longitud del código generado
     * @return el código generado como una cadena de caracteres
     */
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

    public static int sumarIngresos() {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_listaCitas;

        int numCitas = 0;

        try {
            String query = "{CALL sp_sumar_ingresos_mes()}";
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

    public static void tituloTabla(JTable tabla, String[] titulos) {
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        tabla.setModel(modelo);
    }

    /**
     * Llena una tabla con los datos proporcionados.
     * 
     * @param tabla La tabla a llenar.
     * @param datos Los datos a insertar en la tabla tipo List.
     */
    public static void llenarTabla(JTable tabla, List<String[]> datos) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
    }

    public static void insertarEnTabla(JTable tabla, ProductoInventario item) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        // modelo.setRowCount(0);

        String[] fila = new String[5];
        fila[0] = item.getCodigo();
        fila[1] = item.getNombre();
        fila[2] = String.valueOf(item.getPrecio());
        fila[3] = String.valueOf(item.getCantidad());
        fila[4] = String.valueOf(item.total());

        modelo.addRow(fila);
    }
}