package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.PersonaCliente;
import Modelo.ProductoInventario;
import Modelo.ProductoItem;
import Modelo.ReporteVenta;
import Procesos.ProcesoInsert;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;
import Procesos.ProcesoUpdate;
import Vista.Dashboard_UI;
import Vista.RPVenta_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class UI_ReporteVentaController extends PanelController implements ActionListener, ListSelectionListener, FocusListener, ItemListener {

    RPVenta_UI VentaUI;

    JTextField textFieldComboCliente;
    JTextField textFieldComboProducto;
    JTextField textFieldComboServicio;
    DefaultTableModel modeloProducto;

    String titutos[] = {"COD", "Producto/Servicio", "Precio", "Cantidad", "Total"};
    String titutos2[] = {"COD", "Codigo Cliente", "Cliente", "Fecha", "Hora", "Nro de Articulos", "Total", "Impuestos", "Importe Final"};

    JComboBox combos[];

    String msgCombo[] = {
        "Cliente", "Producto", "Servicio"
    };

    boolean buscar = false;

    public UI_ReporteVentaController(RPVenta_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.VentaUI = panel;

        VentaUI.datePicker.setDateToToday();
        VentaUI.timePicker.setTimeToNow();
        VentaUI.btnCancelar.setVisible(false);
        VentaUI.btnModificar.setEnabled(false);
        VentaUI.btnEliminar.setEnabled(false);

        combos = new JComboBox[]{
            VentaUI.cbCliente, VentaUI.cbProducto, VentaUI.cbServicio
        };

        ProcesoListado.tituloTabla(VentaUI.tbProductos, titutos2);
        ProcesoListado.llenarTabla(VentaUI.tbProductos, ProcesoListado.listarDatos("boleta"));
        modeloProducto = (DefaultTableModel) VentaUI.tbProductos.getModel();

        textFieldComboCliente = (JTextField) VentaUI.cbCliente.getEditor().getEditorComponent();
        textFieldComboProducto = (JTextField) VentaUI.cbProducto.getEditor().getEditorComponent();
        textFieldComboServicio = (JTextField) VentaUI.cbServicio.getEditor().getEditorComponent();
        llenarCboClientes();
        llenarCboProducto();
        llenarCboServicio();
        VentaUI.lblTotal.setText("S/.00.00");
        VentaUI.lblImpuesto.setText("S/.00.00");
        VentaUI.lblTotalFinal.setText("S/.00.00");

        String cod = ProcesoListado.generarCodigo("boleta", "id_boleta", "BOL-", 4);
        VentaUI.lblCodigo.setText(cod);
        VentaUI.btnAgregar.setEnabled(false);
        VentaUI.cbCliente.setEnabled(false);
        toggleState(false);

        keyListener();
        addListeners();
    }

    private void keyListener() {
        textFieldComboCliente.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> {
                    ProcesoListado.filterComboBox(textFieldComboCliente.getText(), VentaUI.cbCliente);
                });
            }
        });
        textFieldComboProducto.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> {
                    ProcesoListado.filterComboBoxProductos(textFieldComboProducto.getText(), VentaUI.cbProducto);
                });
            }
        });
        textFieldComboServicio.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> {
                    ProcesoListado.filterComboBoxProductos(textFieldComboServicio.getText(), VentaUI.cbServicio);
                });
            }
        });
    }

    private void llenarCboClientes() {
        VentaUI.cbCliente.removeAllItems();

        ArrayList<PersonaCliente> listaClientes = ProcesoListado.obtenerClientes();
        for (int i = 0; i < listaClientes.size(); i++) {
            VentaUI.cbCliente.addItem(new PersonaCliente(listaClientes.get(i).getCodigo(),
                    listaClientes.get(i).getNombre()));
        }
    }

    private void llenarCboProducto() {
        VentaUI.cbProducto.removeAllItems();

        ArrayList<ProductoInventario> listaProductos = ProcesoListado.obtenerProducto();
        for (int i = 0; i < listaProductos.size(); i++) {
            VentaUI.cbProducto.addItem(new ProductoInventario(listaProductos.get(i).getCodigo(),
                    listaProductos.get(i).getInfo(), listaProductos.get(i).getPrecio()));

        }
    }

    private void llenarCboServicio() {
        VentaUI.cbServicio.removeAllItems();

        ArrayList<ProductoInventario> listaProductos = ProcesoListado.obtenerServicios();
        for (int i = 0; i < listaProductos.size(); i++) {
            VentaUI.cbServicio.addItem(new ProductoInventario(listaProductos.get(i).getCodigo(),
                    listaProductos.get(i).getInfo(),
                    listaProductos.get(i).getPrecio())
            );
        }
    }

    private void toggleState(boolean mode) {
        VentaUI.cbProducto.setEnabled(mode);
        VentaUI.cbServicio.setEnabled(mode);
        VentaUI.spCantidad.setEnabled(mode);
        VentaUI.spPrecioServ.setEnabled(mode);
    }

    public double sumaColumna() {
        double total = 0;
        for (int i = 0; i < VentaUI.tbProductos.getRowCount(); i++) {
            total += Double.parseDouble(VentaUI.tbProductos.getValueAt(i, VentaUI.tbProductos.getColumnCount() - 1).toString());
        }
        return total;
    }

    public void calcularPagos() {
        double total = sumaColumna();
        double impuesto = total * 0.18;
        double importeFinal = total - impuesto;
        VentaUI.lblTotal.setText("S/." + total);
        VentaUI.lblImpuesto.setText("S/." + impuesto);
        VentaUI.lblTotalFinal.setText("S/." + importeFinal);
    }

    @Override
    protected void addListeners() {
        VentaUI.btnRegistrar.addActionListener(this);
        VentaUI.btnBuscar.addActionListener(this);
        VentaUI.btnModificar.addActionListener(this);
        VentaUI.btnEliminar.addActionListener(this);
        VentaUI.btnAgregar.addActionListener(this);
        VentaUI.btnCancelar.addActionListener(this);

        VentaUI.tbProductos.getSelectionModel().addListSelectionListener(this);

        VentaUI.cbCliente.addItemListener(this);
        VentaUI.cbProducto.addItemListener(this);
        VentaUI.cbServicio.addItemListener(this);

        textFieldComboCliente.addFocusListener(this);
        textFieldComboProducto.addFocusListener(this);
        textFieldComboServicio.addFocusListener(this);
    }

    @Override
    protected void reloadWindow() {
        RPVenta_UI newVenta = new RPVenta_UI();
        UI_ReporteVentaController controllerVenta = new UI_ReporteVentaController(newVenta, vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == VentaUI.btnAgregar) {
            VentaUI.btnAgregar.setText("Agregar");
            if (VentaUI.cbCliente.getSelectedIndex() != 0) {
                if (VentaUI.cbProducto.getSelectedIndex() != 0 || VentaUI.cbServicio.getSelectedIndex() != 0) {
                    ProcesoListado.insertarEnTabla(VentaUI.tbProductos, ProcesoInsert.obtenerItem(VentaUI));
                    calcularPagos();
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto oservicio de la lista ╰（‵□′）╯");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una opcion de la lista de Clientes para comenzar ╰（‵□′）╯");
            }
        } else if (e.getSource() == VentaUI.btnRegistrar) {
            if (VentaUI.btnRegistrar.getText().equals("Nuevo")) {
                modeloProducto.setRowCount(0);
                VentaUI.btnRegistrar.setText("Registrar");
                VentaUI.btnAgregar.setEnabled(true);
                VentaUI.cbCliente.setEnabled(true);
                VentaUI.btnCancelar.setVisible(true);
                ProcesoListado.tituloTabla(VentaUI.tbProductos, titutos);
            } else {
                ReporteVenta boleta = new ReporteVenta();
                boleta.setCodigo(VentaUI.lblCodigo.getText());
                boleta.setCodigo_entidad(VentaUI.cbCliente.getItemAt(VentaUI.cbCliente.getSelectedIndex()).getCodigo());
                boleta.setEntidad(VentaUI.cbCliente.getItemAt(VentaUI.cbCliente.getSelectedIndex()).getNombre());
                LocalDate localDate = VentaUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                boleta.setFecha(date);
                LocalTime localTime = VentaUI.timePicker.getTime();
                Time time = Time.valueOf(localTime);
                boleta.setHora(time);
                boleta.setCantidaditems(VentaUI.tbProductos.getRowCount());
                boleta.setImporte(sumaColumna());

                ProcesoInsert.insertarBoleta(boleta);

                for (int i = 0; i < VentaUI.tbProductos.getRowCount(); i++) {
                    ProductoItem item = new ProductoItem();
                    item.setCodigoBoleta(VentaUI.lblCodigo.getText());
                    item.setCodigo(VentaUI.tbProductos.getValueAt(i, 0).toString());
                    item.setNombre(VentaUI.tbProductos.getValueAt(i, 1).toString());
                    item.setPrecio(Double.parseDouble(VentaUI.tbProductos.getValueAt(i, 2).toString()));
                    item.setCantidad(Integer.parseInt(VentaUI.tbProductos.getValueAt(i, 3).toString()));
                    ProcesoInsert.insertarItemBoleta(item);
                }
                reloadWindow();

            }
        } else if (e.getSource() == VentaUI.btnBuscar) {
            if (VentaUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el Codigo de la boleta");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        List<String[]> datos = ProcesoRD.buscarRegistros("v_itemsBoleta", "id_boleta", dato);
                        List<String[]> datoCliente = ProcesoRD.buscarRegistros("boleta", "id_boleta", dato);
                        if (!datos.isEmpty()) {
                            ProcesoListado.tituloTabla(VentaUI.tbProductos, titutos);
                            ProcesoListado.llenarTabla(VentaUI.tbProductos, datos);
                            VentaUI.btnBuscar.setText("Cancelar");
                            buscar = true;
                            calcularPagos();
                            //VentaUI.btnCancelar.setVisible(true);
                            VentaUI.btnModificar.setEnabled(true);
                            VentaUI.btnEliminar.setEnabled(true);
                            VentaUI.btnRegistrar.setEnabled(false);
                            //cliente
                            VentaUI.cbCliente.setEnabled(true);
                            toggleState(true);

                            String[] primerRegistro = datoCliente.get(0);
                            VentaUI.lblCodigo.setText(primerRegistro[0]);

                            for (int i = 1; i < VentaUI.cbCliente.getItemCount(); i++) {
                                String codigoItem = VentaUI.cbCliente.getItemAt(i).getCodigo();
                                if (codigoItem.equals(primerRegistro[1])) {
                                    VentaUI.cbCliente.setSelectedIndex(i);
                                    break;
                                }
                            }

                            String strFecha = primerRegistro[3];
                            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate fecha = LocalDate.parse(strFecha, formato);
                            VentaUI.datePicker.setDate(fecha);

                            String strHora = primerRegistro[4];
                            LocalTime hora = LocalTime.parse(strHora);
                            VentaUI.timePicker.setTime(hora);

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No ingresó ningún dato");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada");
                }
            } else {
                VentaUI.btnBuscar.setText("Buscar");
                VentaUI.btnEliminar.setEnabled(false);
                VentaUI.btnModificar.setEnabled(false);
                VentaUI.btnRegistrar.setEnabled(true);
                reloadWindow();
                buscar = false;
            }

        } else if (e.getSource() == VentaUI.btnModificar) {
            if (VentaUI.cbCliente.getSelectedIndex() != 0) {
                ReporteVenta boleta = new ReporteVenta();
                String codigo = VentaUI.lblCodigo.getText();
                boleta.setCodigo(codigo);
                boleta.setCodigo_entidad(VentaUI.cbCliente.getItemAt(VentaUI.cbCliente.getSelectedIndex()).getCodigo());
                boleta.setEntidad(VentaUI.cbCliente.getItemAt(VentaUI.cbCliente.getSelectedIndex()).getNombre());
                LocalDate localDate = VentaUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                boleta.setFecha(date);
                LocalTime localTime = VentaUI.timePicker.getTime();
                Time time = Time.valueOf(localTime);
                boleta.setHora(time);
                boleta.setCantidaditems(VentaUI.tbProductos.getRowCount());
                boleta.setImporte(sumaColumna());

                ProcesoUpdate.actualizarBoleta(boleta);
                ProcesoRD.eliminarRegistros("itemsboleta", "id_boleta", codigo);

                for (int i = 0; i < VentaUI.tbProductos.getRowCount(); i++) {
                    ProductoItem item = new ProductoItem();
                    item.setCodigoBoleta(VentaUI.lblCodigo.getText());
                    item.setCodigo(VentaUI.tbProductos.getValueAt(i, 0).toString());
                    item.setNombre(VentaUI.tbProductos.getValueAt(i, 1).toString());
                    item.setPrecio(Double.parseDouble(VentaUI.tbProductos.getValueAt(i, 2).toString()));
                    item.setCantidad(Integer.parseInt(VentaUI.tbProductos.getValueAt(i, 3).toString()));
                    ProcesoInsert.insertarItemBoleta(item);
                }
                reloadWindow();
            }

        } else if (e.getSource() == VentaUI.btnEliminar) {
            ProcesoRD.eliminarRegistros("boleta", "id_boleta", VentaUI.lblCodigo.getText());
            VentaUI.btnBuscar.setText("Buscar");
            VentaUI.btnEliminar.setEnabled(false);
            VentaUI.btnModificar.setEnabled(false);
            reloadWindow();
        } else if (e.getSource() == VentaUI.btnCancelar) {
            reloadWindow();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        // Obtiene el modelo de selección de la lista del evento
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        DefaultTableModel modelo = (DefaultTableModel) VentaUI.tbProductos.getModel();

        //if (e.getSource()==VentaUI.tbProductos.getSelectionModel()) 
        // Verifica si hay alguna fila seleccionada
        if (!lsm.isSelectionEmpty() && buscar) {
            // Obtiene el índice de la fila seleccionada
            int filaSeleccionada = lsm.getMinSelectionIndex();

            String[] datos = new String[VentaUI.tbProductos.getColumnCount()];
            for (int i = 0; i < VentaUI.tbProductos.getColumnCount(); i++) {
                datos[i] = (String) VentaUI.tbProductos.getValueAt(filaSeleccionada, i);
            }

            VentaUI.btnEliminar.setEnabled(true);
            VentaUI.btnModificar.setEnabled(true);

            String idItem = datos[0];

            for (int i = 1; i < VentaUI.cbProducto.getItemCount(); i++) {
                String codigoItem = VentaUI.cbProducto.getItemAt(i).getCodigo();
                if (codigoItem.equals(idItem)) {
                    VentaUI.cbProducto.setSelectedIndex(i);
                    VentaUI.spCantidad.setValue(Integer.parseInt(datos[3]));
                    break;
                } else {
                    VentaUI.cbProducto.setSelectedIndex(0);
                }
            }
            for (int i = 1; i < VentaUI.cbServicio.getItemCount(); i++) {
                String codigoItem = VentaUI.cbServicio.getItemAt(i).getCodigo();
                if (codigoItem.equals(idItem)) {
                    VentaUI.cbServicio.setSelectedIndex(i);
                    VentaUI.spPrecioServ.setValue(Double.parseDouble(datos[4]));
                    break;
                } else {
                    VentaUI.cbServicio.setSelectedIndex(0);
                }
            }

            modelo.removeRow(filaSeleccionada);
            calcularPagos();
            VentaUI.btnAgregar.setEnabled(true);
            VentaUI.btnAgregar.setText("Modificar");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == textFieldComboCliente) {
            String enteredText = textFieldComboCliente.getText();
            if (enteredText.equals("[Selecciona una opción...]")) {
                textFieldComboCliente.setText("");
                VentaUI.cbCliente.showPopup();
            }
        } else if (e.getSource() == textFieldComboProducto) {
            String enteredText = textFieldComboProducto.getText();
            if (enteredText.equals("[Selecciona una opción...]")) {
                textFieldComboProducto.setText("");
                VentaUI.cbProducto.showPopup();
            }
        } else if (e.getSource() == textFieldComboServicio) {
            String enteredText = textFieldComboServicio.getText();
            if (enteredText.equals("[Selecciona una opción...]")) {
                textFieldComboServicio.setText("");
                VentaUI.cbServicio.showPopup();
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == textFieldComboCliente) {
            String enteredText = textFieldComboCliente.getText();
            boolean isPresent = false;
            for (PersonaCliente cliente : ProcesoListado.obtenerClientes()) {
                if (cliente.toString().equals(enteredText)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                llenarCboClientes();
                VentaUI.cbCliente.setSelectedIndex(0);
            }
        } else if (e.getSource() == textFieldComboProducto) {
            String enteredText = textFieldComboProducto.getText();
            boolean isPresent = false;
            for (ProductoInventario productos : ProcesoListado.obtenerProducto()) {
                if (productos.toString().equals(enteredText)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                llenarCboProducto();
                VentaUI.cbProducto.setSelectedIndex(0);
            }
        } else if (e.getSource() == textFieldComboServicio) {
            String enteredText = textFieldComboServicio.getText();
            boolean isPresent = false;
            for (ProductoInventario servicios : ProcesoListado.obtenerServicios()) {
                if (servicios.toString().equals(enteredText)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                llenarCboServicio();
                VentaUI.cbServicio.setSelectedIndex(0);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getSource() == VentaUI.cbCliente) {
                if (VentaUI.cbCliente.getSelectedIndex() != 0) {
                    toggleState(true);

                } else {
                    toggleState(false);
                }
            } else if (e.getSource() == VentaUI.cbProducto) {
                if (VentaUI.cbProducto.getSelectedIndex() != 0) {
                    VentaUI.cbServicio.setSelectedIndex(0);
                    VentaUI.spPrecioServ.setValue(0);
                }
            } else if (e.getSource() == VentaUI.cbServicio) {
                if (VentaUI.cbServicio.getSelectedIndex() != 0) {
                    VentaUI.cbProducto.setSelectedIndex(0);
                    VentaUI.spCantidad.setValue(0);
                    double precio = VentaUI.cbServicio.getItemAt(VentaUI.cbServicio.getSelectedIndex()).getPrecio();
                    VentaUI.spPrecioServ.setValue(precio);
                }
            }
        }
    }

}
