package DAO;

import java.util.List;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Modelo.PersonaEmpleado;

public class DAOPersonal extends ConexionDB {
    public void insertarPersonal(PersonaEmpleado empleado) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_personal(?,?,?,?,?,?,?,?)}");
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
                JOptionPane.showMessageDialog(null, "Error al insertar personal (˘･_･˘)", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }

    public void actualizarPersonal(PersonaEmpleado empleado) {
        CallableStatement cs_update;
        try {
            cs_update = conectar.prepareCall("{CALL sp_actualizar_personal(?,?,?,?,?,?,?,?)}");
            cs_update.setString(1, empleado.getCodigo());
            cs_update.setString(2, empleado.getNombre());
            cs_update.setString(3, empleado.getApellido());
            cs_update.setString(4, empleado.getContraseña());
            cs_update.setString(5, empleado.getCorreo());
            cs_update.setInt(6, empleado.getDni());
            cs_update.setString(7, empleado.getCargo());
            cs_update.setString(8, empleado.getNickname());
            int resultado = cs_update.executeUpdate();
            cs_update.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Personal modificado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }

    // metodo void eliminar personal
    public void eliminarPersonal(String codigo) {
        MetodosReadDelete.eliminarRegistros("personal", "id_personal", codigo);
    }

    // metodo buscar personal
    public List<String[]> buscarPersonal(String dato) {
        return MetodosReadDelete.buscarRegistros("personal", "dni", dato);
    }

    // metodo listar personal return List<String[]>
    public List<String[]> listarPersonal() {
        return MetodosList.listarDatos("v_personal");
    }

}
