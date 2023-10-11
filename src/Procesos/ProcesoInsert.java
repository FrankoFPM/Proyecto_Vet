package Procesos;

import DB.Conexion;
import Modelo.Paciente;
import Modelo.PersonaCliente;
import Modelo.PersonaEmpleado;
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
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente Registrado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar cliente (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }

    public static void insertarPaciente(Paciente paciente) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_paciente(?,?,?,?,?,?,?)}");
            cs_insert.setString(1, paciente.getCodigo());
            cs_insert.setString(2, paciente.getNombre());
            cs_insert.setString(3, paciente.getEspecie());
            cs_insert.setString(4, paciente.getRaza());
            cs_insert.setString(5, paciente.getSexo());
            cs_insert.setString(6, paciente.getColor());
            cs_insert.setString(7, paciente.getDueño());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Paciente Registrado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar paciente (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }
    public static void insertarPersonal(PersonaEmpleado empleado) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_personal(?,?,?,?,?,?,?,?)}");
            cs_insert.setString(1, empleado.getCodigo());
            cs_insert.setString(2, empleado.getNombre());
            cs_insert.setString(3, empleado.getApellido());
            cs_insert.setString(4, empleado.getContraseña());
            cs_insert.setString(5, empleado.getCorreo());
            cs_insert.setInt(6, empleado.getDni());
            cs_insert.setString(7, empleado.getCargo());
            cs_insert.setString(8, empleado.getNickname());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Personal Registrado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar personal (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }
}
