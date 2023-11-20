package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import DB.Conexion;
import Modelo.ReporteClinico;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;

public class DAOReporteClinico extends ConexionDB {
    public void insertarReporteClinico(ReporteClinico rpClinico) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_reporteclinico(?,?,?,?,?,?,?,?,?)}");
            cs_insert.setString(1, rpClinico.getCodigo());
            cs_insert.setString(2, rpClinico.getCodigo_entidad());
            cs_insert.setString(3, rpClinico.getEntidad());
            cs_insert.setString(4, rpClinico.getCausa());
            cs_insert.setString(5, rpClinico.getPronostico());
            cs_insert.setString(6, rpClinico.getSintomas());
            cs_insert.setString(7, rpClinico.getTratamiento());
            cs_insert.setDate(8, rpClinico.getFecha());
            cs_insert.setTime(9, rpClinico.getHora());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Reporte clinico guardado", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarReporteClinico(ReporteClinico rpClinico) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_update;
        try {
            cs_update = cn.prepareCall("{CALL sp_actualizar_reporteclinico(?,?,?,?,?,?,?,?,?)}");
            cs_update.setString(1, rpClinico.getCodigo());
            cs_update.setString(2, rpClinico.getCodigo_entidad());
            cs_update.setString(3, rpClinico.getEntidad());
            cs_update.setString(4, rpClinico.getCausa());
            cs_update.setString(5, rpClinico.getPronostico());
            cs_update.setString(6, rpClinico.getSintomas());
            cs_update.setString(7, rpClinico.getTratamiento());
            cs_update.setDate(8, rpClinico.getFecha());
            cs_update.setTime(9, rpClinico.getHora());
            int resultado = cs_update.executeUpdate();
            cs_update.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Reporte clinico modificado", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metodo eliminar reporte clinico void
    public void eliminarReporteClinico(String codigo) {
        ProcesoRD.eliminarRegistros("ReporteClinico", "id_rpclinico", codigo);
    }

    // metodo listar reporte clinico retonra un List<String[]>
    public List<String[]> listarReporteClinico() {
        return ProcesoListado.listarDatos("ReporteClinico");
    }

    // metodo buscar reporte clinico retorna List<String[]>
    public List<String[]> buscarReporteClinico(String codigo) {
        return ProcesoRD.buscarRegistros("ReporteClinico", "id_rpclinico", codigo);
    }
}
