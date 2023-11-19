package DAO;

import Modelo.PersonaCliente;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;

/**
 * Esta clase representa un objeto DAOCliente que se encarga de realizar
 * operaciones relacionadas con la tabla "cliente" en la base de datos.
 */

public class DAOCliente extends ConexionDB {

    public void insertarCliente(PersonaCliente cliente) {
        CallableStatement cs_insert;
        try {
            cs_insert = conectar.prepareCall("{CALL sp_insertar_cliente(?,?,?,?,?,?,?)}");
            cs_insert.setString(1, cliente.getCodigo());
            cs_insert.setString(2, cliente.getNombre());
            cs_insert.setString(3, cliente.getApellido());
            cs_insert.setString(4, cliente.getTelefono());
            cs_insert.setString(5, cliente.getCorreo());
            cs_insert.setInt(6, cliente.getDni());
            cs_insert.setString(7, cliente.getDireccion());
            int resultado = cs_insert.executeUpdate();
            cs_insert.close();
            conectar.close();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null,
                        "Cliente " + cliente.getNombre() + " " + cliente.getApellido() + " registrado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar cliente (˘･_･˘)", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }

    public void actualizarCliente(PersonaCliente cliente) {
        CallableStatement cs_update;
        try {
            cs_update = conectar.prepareCall("{CALL sp_actualizar_cliente(?,?,?,?,?,?,?)}");
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
                JOptionPane.showMessageDialog(null,
                        "Cliente " + cliente.getNombre() + " " + cliente.getApellido() + " modificado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el cliente", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
        }
    }

    // metodo eliminar cliente
    public void eliminarCliente(String codigo) {
        ProcesoRD.eliminarRegistros("cliente", "id_cliente", codigo);
    }

    /**
     * Método que lista los clientes.
     */
    public void listarCliente() {
        ProcesoListado.listarDatos("cliente");
    }

    /**
     * Busca un cliente en la base de datos utilizando el código proporcionado.
     * 
     * @param codigo El código del cliente a buscar.
     * @return Una lista de arreglos de cadenas que representan los registros
     *         encontrados.
     */
    public List<String[]> buscarCliente(String codigo) {
        return ProcesoRD.buscarRegistros("cliente", "dni", codigo);
    }
}
