package Procesos;

import DB.Conexion;
import Modelo.PersonaCliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProcesoInsert {

    /*
    Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_genCodigo;
     */
    public static void insertarCliente(PersonaCliente cliente) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_cliente(?,?,?,?,?,?,?)}");
            cs_insert.setString(1, cliente.getCodigo());
            cs_insert.setString(2, cliente.getNombre());
            cs_insert.setString(3, cliente.getApellido());
            cs_insert.setString(4, cliente.getTelefono());
            cs_insert.setString(5, cliente.getCorreo());
            cs_insert.setInt(6, cliente.getDni());
            cs_insert.setString(7, cliente.getDireccion());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado>0) {
                JOptionPane.showMessageDialog(null, "Cliente Registrado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar cliente (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }
}
