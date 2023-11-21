package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import DB.Conexion;
import Modelo.ProductoInventario;
import Modelo.ProductoItem;
import Modelo.ReporteVenta;
import Vista.RPVenta_UI;

public class DAOReporteVenta extends ConexionDB {
    public void insertarItemBoleta(ProductoItem item) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_itemboleta(?,?,?,?,?,?)}");
            cs_insert.setString(1, item.getCodigoBoleta());
            cs_insert.setString(2, item.getCodigo());
            cs_insert.setString(3, item.getNombre());
            cs_insert.setDouble(4, item.getPrecio());
            cs_insert.setInt(5, item.getCantidad());
            cs_insert.setDouble(6, item.total());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Productos registrados", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarBoleta(ReporteVenta boleta) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_boleta(?,?,?,?,?,?,?,?,?)}");
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
                JOptionPane.showMessageDialog(null, "Productos registrados", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar (˘･_･˘)", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarBoleta(ReporteVenta boleta) {
        CallableStatement cs_update;
        try {
            cs_update = conectar.prepareCall("{CALL sp_actualizar_boleta(?,?,?,?,?,?,?,?,?)}");
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

    public ProductoInventario obtenerItem(RPVenta_UI ui) {
        ProductoInventario item = new ProductoInventario();
        if (ui.cbProducto.getSelectedIndex() != 0) {
            item.setCodigo(ui.cbProducto.getItemAt(ui.cbProducto.getSelectedIndex()).getCodigo());
            item.setNombre(ui.cbProducto.getItemAt(ui.cbProducto.getSelectedIndex()).getInfo());
            ;
            item.setCantidad((int) ui.spCantidad.getValue());
            item.setPrecio((double) ui.cbProducto.getItemAt(ui.cbProducto.getSelectedIndex()).getPrecio());
        } else if (ui.cbServicio.getSelectedIndex() != 0) {
            item.setCodigo(ui.cbServicio.getItemAt(ui.cbServicio.getSelectedIndex()).getCodigo());
            item.setNombre(ui.cbServicio.getItemAt(ui.cbServicio.getSelectedIndex()).getInfo());
            item.setCantidad(1);
            item.setPrecio(((Double) ui.spPrecioServ.getValue()).doubleValue());
        }
        return item;
    }

    // metodo para listar boletas retornando un List<String[]>
    public List<String[]> listarBoletas() {
        return MetodosList.listarDatos("boleta");
    }

    // listar items boleta retornando un List<String[]>
    public List<String[]> listarItemsBoleta(String codigoBoleta) {
        return MetodosReadDelete.buscarRegistros("v_itemsBoleta", "id_boleta", codigoBoleta);
    }

    // listar boletas retornando un List<String[]>
    public List<String[]> listarBoletas(String codigoBoleta) {
        return MetodosReadDelete.buscarRegistros("boleta", "id_boleta", codigoBoleta);
    }

    // eliminar items boleta
    public void eliminarItemsBoleta(String codigoBoleta) {
        MetodosReadDelete.eliminarRegistros("itemsboleta", "id_boleta", codigoBoleta);
    }

    // eliminar boleta
    public void eliminarBoleta(String codigoBoleta) {
        MetodosReadDelete.eliminarRegistros("boleta", "id_boleta", codigoBoleta);
    }

}
