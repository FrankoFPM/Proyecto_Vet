package Procesos;

import DB.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JTable;
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

    public static ArrayList<String[]> listarDatos(String tabla) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_listaClientes;

        ArrayList<String[]> datos = new ArrayList<>();

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
    
    public static void tituloTabla(JTable tabla,String[] titulos){
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        tabla.setModel(modelo);
    }
    
    public static void llenarTabla(JTable tabla, ArrayList<String[]> datos) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
    }
}
