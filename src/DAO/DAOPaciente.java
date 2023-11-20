package DAO;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Paciente;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;

/**
 * Esta clase representa un objeto DAO (Data Access Object) para la entidad
 * Paciente.
 * Proporciona métodos para listar y buscar pacientes en la base de datos.
 */
public class DAOPaciente extends ConexionDB {

    // metodo listar pacientes
    public List<String[]> listarPacientes() {
        return ProcesoListado.listarDatos("paciente");
    }

    // metodo buscar paciente
    public List<String[]> buscarPaciente(String dato) {
        return ProcesoRD.buscarRegistros("paciente", "id_cliente", dato);
    }

    public void insertarPaciente(Paciente paciente) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_paciente(?,?,?,?,?,?,?)}");
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
                JOptionPane.showMessageDialog(null, "Error al insertar paciente (˘･_･˘)", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }

    public void actualizarPaciente(Paciente paciente) {
        CallableStatement cs_update;
        try {
            cs_update = conectar.prepareCall("{CALL sp_actualizar_paciente(?,?,?,?,?,?,?)}");
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

    // metodo eliminar paciente
    public void eliminarPaciente(String codigo) {
        ProcesoRD.eliminarRegistros("paciente", "id_paciente", codigo);
    }
}
