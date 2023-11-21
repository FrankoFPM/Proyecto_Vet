package DAO;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import Modelo.ProductoInventario;

public class DAOProducto extends ConexionDB {
    public void insertarProducto(ProductoInventario producto) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_producto(?,?,?,?,?,?,?)}");
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

    public void actualizarProducto(ProductoInventario producto) {
        CallableStatement cs_update;
        try {
            cs_update = conectar.prepareCall("{CALL sp_actualizar_producto(?,?,?,?,?,?,?)}");
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

    // metodo eliminar producto
    public void eliminarProducto(String codigo) {
        MetodosReadDelete.eliminarRegistros("Productos", "id_producto", codigo);
    }

    // metodo buscar producto retorna un List<String[]>
    public List<String[]> buscarProducto(String codigo) {
        return MetodosReadDelete.buscarRegistros("Productos", "id_producto", codigo);
    }

    // metodo listar productos retorna un List<String[]>
    public List<String[]> listarProductos() {
        return MetodosList.listarDatos("productos");
    }
}
