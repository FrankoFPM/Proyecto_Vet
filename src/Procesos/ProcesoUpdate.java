package Procesos;

import DB.Conexion;
import Modelo.Paciente;
import Modelo.PersonaCliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProcesoUpdate {
    /*
    Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_genCodigo;
     */

    public static void actualizarCliente(PersonaCliente cliente) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_update;
        try {
            cs_update = cn.prepareCall("{CALL sp_actualizar_cliente(?,?,?,?,?,?,?)}");
            cs_update.setString(1, cliente.getCodigo());
            cs_update.setString(2, cliente.getNombre());
            cs_update.setString(3, cliente.getApellido());
            cs_update.setString(4, cliente.getTelefono());
            cs_update.setString(5, cliente.getCorreo());
            cs_update.setInt(6, cliente.getDni());
            cs_update.setString(7, cliente.getDireccion());
            int resultado = cs_update.executeUpdate();
            cs_update.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente modificado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }

    public static void actualizarPaciente(Paciente paciente) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_update;
        try {
            cs_update = cn.prepareCall("{CALL sp_actualizar_paciente(?,?,?,?,?,?,?)}");
            cs_update.setString(1, paciente.getCodigo());
            cs_update.setString(2, paciente.getNombre());
            cs_update.setString(3, paciente.getEspecie());
            cs_update.setString(4, paciente.getRaza());
            cs_update.setString(5, paciente.getSexo());
            cs_update.setString(6, paciente.getColor());
            cs_update.setString(7, paciente.getDueño());
            int resultado = cs_update.executeUpdate();
            cs_update.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Paciente modificado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }
}
