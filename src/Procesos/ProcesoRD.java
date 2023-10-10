package Procesos;

import DB.Conexion;
import java.awt.HeadlessException;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * This class is for read and delete
 */
public class ProcesoRD {

    /*
    Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_genCodigo;
     */
    public static void eliminarRegistros(String tabla, String columna, String codigo) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_eliminar;
        try {
            String query = "{call sp_eliminar(?,?,?)}";
            cs_eliminar = cn.prepareCall(query);
            cs_eliminar.setString(1, tabla);
            cs_eliminar.setString(2, columna);
            cs_eliminar.setString(3, codigo);
            int resultado = cs_eliminar.executeUpdate();
            cs_eliminar.close();
            if (resultado>0) {
                JOptionPane.showMessageDialog(null, tabla + " eliminado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (HeadlessException | SQLException e) {
        }
    }

    public static List<String[]> buscarRegistros(String tabla, String columna, String codigo) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_buscar;
        List<String[]> filas = new ArrayList<>();

        try {
            String query = "{call sp_buscar(?,?,?)}";
            cs_buscar = cn.prepareCall(query);
            cs_buscar.setString(1, tabla);
            cs_buscar.setString(2, columna);
            cs_buscar.setString(3, codigo);
            boolean resultado = cs_buscar.execute();
            if (resultado) {
                ResultSet rs = cs_buscar.getResultSet();

                while (rs.next()) {
                    String[] fila = new String[rs.getMetaData().getColumnCount()];
                    for (int i = 0; i < fila.length; i++) {
                        fila[i] = rs.getString(i + 1);
                    }
                    filas.add(fila);
                }
                rs.close();
                if (!filas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Registro encontrado", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Registro no encontrado", "Warning", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al buscar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
            cs_buscar.close();
        } catch (HeadlessException | SQLException e) {
        }
        return filas;
    }
}
