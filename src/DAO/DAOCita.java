package DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import DB.Conexion;
import Modelo.Cita;
import java.awt.HeadlessException;

public class DAOCita extends ConexionDB {
    public void insertarCita(Cita reserva) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_cita(?,?,?,?,?,?,?,?)}");
            cs_insert.setString(1, reserva.getCodigo());
            cs_insert.setString(2, reserva.getCodigoCliente());
            cs_insert.setString(3, reserva.getCliente());
            cs_insert.setString(4, reserva.getPaciente());
            cs_insert.setString(5, reserva.getVeterinario());
            cs_insert.setDate(6, reserva.getFecha());
            cs_insert.setTime(7, reserva.getHora());
            cs_insert.setString(8, reserva.getEstado());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cita reservada", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al reservar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCita(Cita reserva) {
        CallableStatement cs_update;
        try {
            cs_update = conectar.prepareCall("{CALL sp_actualizar_cita(?,?,?,?,?,?,?,?)}");
            cs_update.setString(1, reserva.getCodigo());
            cs_update.setString(2, reserva.getCodigoCliente());
            cs_update.setString(3, reserva.getCliente());
            cs_update.setString(4, reserva.getPaciente());
            cs_update.setString(5, reserva.getVeterinario());
            cs_update.setDate(6, reserva.getFecha());
            cs_update.setTime(7, reserva.getHora());
            cs_update.setString(8, reserva.getEstado());
            int resultado = cs_update.executeUpdate();
            cs_update.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cita modificada", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metodo listar
    public List<String[]> listarCitas() {
        return MetodosList.listarDatos("cita");
    }

    // metodo buscar retorna List<String[]>
    public List<String[]> buscarCitas(String dni) {
        CallableStatement cs_buscar;
        List<String[]> filas = new ArrayList<>();

        try {
            String query = "{call buscar_citas(?)}";
            cs_buscar = conectar.prepareCall(query);
            cs_buscar.setString(1, dni);
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
                    JOptionPane.showMessageDialog(null, "Citas encontradas", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No hay citas registradas", "Warning",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al buscar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
            cs_buscar.close();
        } catch (HeadlessException | SQLException e) {
        }
        return filas;
    }

    // metodo eliminar
    public void eliminarCita(String codigo) {
        MetodosReadDelete.eliminarRegistros("cita", "id_cita", codigo);
    }
}
