package Procesos;

import DB.Conexion;
import Modelo.Cita;
import Modelo.Paciente;
import Modelo.PersonaCliente;
import Modelo.PersonaEmpleado;
import Modelo.ProductoInventario;
import Modelo.ReporteClinico;
import Modelo.ReporteVenta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * ProcesoUpdate.java is deprecated.
 */
public class ProcesoUpdate {
    /*
     * Conexion objConn = new Conexion();
     * Connection cn = objConn.ObtenerConexion();
     * CallableStatement cs_genCodigo;
     */

    private static void actualizarCliente(PersonaCliente cliente) {
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

    private static void actualizarPaciente(Paciente paciente) {
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

    private static void actualizarPersonal(PersonaEmpleado empleado) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_update;
        try {
            cs_update = cn.prepareCall("{CALL sp_actualizar_personal(?,?,?,?,?,?,?,?)}");
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

    private static void actualizarCita(Cita reserva) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_update;
        try {
            cs_update = cn.prepareCall("{CALL sp_actualizar_cita(?,?,?,?,?,?,?,?)}");
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

    private static void actualizarReporteClinico(ReporteClinico rpClinico) {
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

    private static void actualizarProducto(ProductoInventario producto) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_update;
        try {
            cs_update = cn.prepareCall("{CALL sp_actualizar_producto(?,?,?,?,?,?,?)}");
            cs_update.setString(1, producto.getCodigo());
            cs_update.setString(2, producto.getNombre());
            cs_update.setString(3, producto.getMarca());
            cs_update.setDouble(4, producto.getPrecio());
            cs_update.setInt(5, producto.getCantidad());
            cs_update.setString(6, producto.getCategoria());
            cs_update.setDate(7, producto.getFecha());
            int resultado = cs_update.executeUpdate();
            cs_update.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Producto modificado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void actualizarBoleta(ReporteVenta boleta) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_update;
        try {
            cs_update = cn.prepareCall("{CALL sp_actualizar_boleta(?,?,?,?,?,?,?,?,?)}");
            cs_update.setString(1, boleta.getCodigo());
            cs_update.setString(2, boleta.getCodigo_entidad());
            cs_update.setString(3, boleta.getEntidad());
            cs_update.setDate(4, boleta.getFecha());
            cs_update.setTime(5, boleta.getHora());
            cs_update.setInt(6, boleta.getCantidaditems());
            cs_update.setDouble(7, boleta.getImporte());
            cs_update.setDouble(8, boleta.impuesto());
            cs_update.setDouble(9, boleta.importeFinal());
            int resultado = cs_update.executeUpdate();
            cs_update.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Boleta modificado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
