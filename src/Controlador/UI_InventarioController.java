package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.ProductoInventario;
import Procesos.ProcesoListado;
import Procesos.ProcesoValidacion;
import Vista.Dashboard_UI;
import Vista.Inventario_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DAO.DAOInventario;
import DAO.DAOProducto;

public class UI_InventarioController extends PanelController implements ActionListener {

    Inventario_UI InventarioUI;
    DAOProducto daoProducto;

    String titutos[] = { "COD", "Nombre", "Marca", "Precio",
            "Cantidad", "Categoria", "Fecha" };

    String[] msgInventario = { "Nombre", "Marca" };
    JTextField[] txtinventario;

    public UI_InventarioController(Inventario_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.InventarioUI = panel;

        txtinventario = new JTextField[] { InventarioUI.txtNombres, InventarioUI.txtMarca };
        ProcesoValidacion.placeholderJtxt(txtinventario, msgInventario);
        ProcesoListado.tituloTabla(InventarioUI.tbProductos, titutos);
        daoProducto = new DAOProducto();
        // ProcesoListado.llenarTabla(InventarioUI.tbProductos,
        // ProcesoListado.listarDatos("productos"));
        ProcesoListado.llenarTabla(InventarioUI.tbProductos, daoProducto.listarProductos());

        String cod = ProcesoListado.generarCodigo("Productos", "id_producto", "PRO-", 4);
        InventarioUI.lblCodigo.setText(cod);
        InventarioUI.btnEliminar.setEnabled(false);
        InventarioUI.btnModificar.setEnabled(false);

        addListeners();
    }

    @Override
    protected void addListeners() {
        InventarioUI.btnRegistrar.addActionListener(this);
        InventarioUI.btnBuscar.addActionListener(this);
        InventarioUI.btnModificar.addActionListener(this);
        InventarioUI.btnEliminar.addActionListener(this);
    }

    @Override
    protected void reloadWindow() {
        Inventario_UI newinventario = new Inventario_UI();
        UI_InventarioController inventarioController = new UI_InventarioController(newinventario, vista);
        System.out.println("Controlador.UI_InventarioController.reloadWindow()");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == InventarioUI.btnEliminar) {
            daoProducto = new DAOProducto();
            // ProcesoRD.eliminarRegistros("Productos", "id_producto",
            // InventarioUI.lblCodigo.getText());
            daoProducto.eliminarProducto(InventarioUI.lblCodigo.getText());
            InventarioUI.btnBuscar.setText("Buscar");
            InventarioUI.btnEliminar.setEnabled(false);
            InventarioUI.btnModificar.setEnabled(false);
            reloadWindow();
        } else if (e.getSource() == InventarioUI.btnRegistrar) {
            if (ProcesoValidacion.validarInputs(txtinventario, msgInventario)) {
                ProductoInventario producto = new ProductoInventario();
                producto.setCodigo(InventarioUI.lblCodigo.getText());
                producto.setNombre(txtinventario[0].getText());
                producto.setMarca(txtinventario[1].getText());
                producto.setPrecio(Double.parseDouble(InventarioUI.spPrecio.getValue().toString()));
                producto.setCantidad(Integer.parseInt(InventarioUI.spCantidad.getValue().toString()));
                producto.setCategoria(InventarioUI.cbCategoria.getSelectedItem().toString());
                LocalDate localDate = InventarioUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                producto.setFecha(date);

                // ProcesoInsert.insertarProducto(producto);
                daoProducto = new DAOProducto();
                daoProducto.insertarProducto(producto);
                reloadWindow();
            }
        } else if (e.getSource() == InventarioUI.btnBuscar) {
            if (InventarioUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el Codigo del producto");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        // List<String[]> datos = ProcesoRD.buscarRegistros("Productos", "id_producto",
                        // dato);
                        daoProducto = new DAOProducto();
                        List<String[]> datos = daoProducto.buscarProducto(dato);
                        if (!datos.isEmpty()) {
                            String[] primerRegistro = datos.get(0);
                            InventarioUI.lblCodigo.setText(primerRegistro[0]);
                            InventarioUI.txtNombres.setText(primerRegistro[1]);
                            InventarioUI.txtMarca.setText(primerRegistro[2]);
                            InventarioUI.spPrecio.setValue(Double.valueOf(primerRegistro[3]));
                            InventarioUI.spCantidad.setValue(Integer.valueOf(primerRegistro[4]));
                            InventarioUI.cbCategoria.setSelectedItem(primerRegistro[5]);

                            String strFecha = primerRegistro[6];
                            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate fecha = LocalDate.parse(strFecha, formato);

                            InventarioUI.datePicker.setDate(fecha);

                            InventarioUI.btnEliminar.setEnabled(true);
                            InventarioUI.btnModificar.setEnabled(true);
                            InventarioUI.btnBuscar.setText("Cancelar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No ingresó ningún dato");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada");
                }
            } else {
                InventarioUI.btnBuscar.setText("Buscar");
                InventarioUI.btnEliminar.setEnabled(false);
                InventarioUI.btnModificar.setEnabled(false);
                reloadWindow();
            }
        } else if (e.getSource() == InventarioUI.btnModificar) {
            if (ProcesoValidacion.validarInputs(txtinventario, msgInventario)) {
                ProductoInventario producto = new ProductoInventario();
                producto.setCodigo(InventarioUI.lblCodigo.getText());
                producto.setNombre(txtinventario[0].getText());
                producto.setMarca(txtinventario[1].getText());
                producto.setPrecio(Double.parseDouble(InventarioUI.spPrecio.getValue().toString()));
                producto.setCantidad(Integer.parseInt(InventarioUI.spCantidad.getValue().toString()));
                producto.setCategoria(InventarioUI.cbCategoria.getSelectedItem().toString());
                LocalDate localDate = InventarioUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                producto.setFecha(date);
                // ProcesoUpdate.actualizarProducto(producto);
                daoProducto = new DAOProducto();
                daoProducto.actualizarProducto(producto);
                reloadWindow();
            }
        }
    }

}
