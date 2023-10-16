package Procesos;

import DB.Conexion;
import Modelo.Cita;
import Modelo.Paciente;
import Modelo.PersonaCliente;
import Modelo.PersonaEmpleado;
import Modelo.ProductoInventario;
import Modelo.ProductoItem;
import Modelo.ReporteClinico;
import Modelo.ReporteVenta;
import Vista.RPVenta_UI;
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

    public static void insertarCita(Cita reserva) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_cita(?,?,?,?,?,?,?,?)}");
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

    public static void insertarReporteClinico(ReporteClinico rpClinico) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_reporteclinico(?,?,?,?,?,?,?,?,?)}");
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
                JOptionPane.showMessageDialog(null, "Reporte clinico guardado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarProducto(ProductoInventario producto) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_producto(?,?,?,?,?,?,?)}");
            cs_insert.setString(1, producto.getCodigo());
            cs_insert.setString(2, producto.getNombre());
            cs_insert.setString(3, producto.getMarca());
            cs_insert.setDouble(4, producto.getPrecio());
            cs_insert.setInt(5, producto.getCantidad());
            cs_insert.setString(6, producto.getCategoria());
            cs_insert.setDate(7, producto.getFecha());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Producto registrado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertarItemBoleta(ProductoItem item) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_itemboleta(?,?,?,?,?,?)}");
            cs_insert.setString(1, item.getCodigoBoleta());
            cs_insert.setString(2, item.getCodigo());
            cs_insert.setString(3, item.getNombre());
            cs_insert.setDouble(4, item.getPrecio());
            cs_insert.setInt(5, item.getCantidad());
            cs_insert.setDouble(6, item.total());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Productos registrados", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertarBoleta(ReporteVenta boleta) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_insert;
        try {
            cs_insert = cn.prepareCall("{CALL sp_insertar_boleta(?,?,?,?,?,?,?,?,?)}");
            cs_insert.setString(1, boleta.getCodigo());
            cs_insert.setString(2, boleta.getCodigo_entidad());
            cs_insert.setString(3, boleta.getEntidad());
            cs_insert.setDate(4, boleta.getFecha());
            cs_insert.setTime(5, boleta.getHora());
            cs_insert.setInt(6, boleta.getCantidaditems());
            cs_insert.setDouble(7, boleta.getImporte());
            cs_insert.setDouble(8, boleta.impuesto());
            cs_insert.setDouble(9, boleta.importeFinal());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Productos registrados", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ProductoInventario obtenerItem(RPVenta_UI ui) {
        ProductoInventario item = new ProductoInventario();
        if (ui.cbProducto.getSelectedIndex() != 0) {
            item.setCodigo(ui.cbProducto.getItemAt(ui.cbProducto.getSelectedIndex()).getCodigo());
            item.setNombre(ui.cbProducto.getItemAt(ui.cbProducto.getSelectedIndex()).getInfo());;
            item.setCantidad((int) ui.spCantidad.getValue());
            item.setPrecio((double) ui.cbProducto.getItemAt(    ui.cbProducto.getSelectedIndex()).getPrecio());
        } else if (ui.cbServicio.getSelectedIndex() != 0) {
            item.setCodigo(ui.cbServicio.getItemAt(ui.cbServicio.getSelectedIndex()).getCodigo());
            item.setNombre(ui.cbServicio.getItemAt(ui.cbServicio.getSelectedIndex()).getInfo());;
            item.setCantidad(1);
            item.setPrecio(((Double) ui.spPrecioServ.getValue()).doubleValue());
        }
        return item;
    }
}
