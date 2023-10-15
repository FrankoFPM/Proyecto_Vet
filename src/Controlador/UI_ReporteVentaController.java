package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.PersonaCliente;
import Modelo.ProductoInventario;
import Procesos.ProcesoInsert;
import Procesos.ProcesoListado;
import Procesos.ProcesoValidacion;
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
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

    JComboBox combos[];

    String msgCombo[] = {
        "Cliente", "Producto", "Servicio"
    };

    public UI_ReporteVentaController(RPVenta_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.VentaUI = panel;

        VentaUI.datePicker.setDateToToday();
        VentaUI.timePicker.setTimeToNow();

        combos = new JComboBox[]{
            VentaUI.cbCliente, VentaUI.cbProducto, VentaUI.cbServicio
        };

        ProcesoListado.tituloTabla(VentaUI.tbProductos, titutos);
        ProcesoListado.llenarTabla(VentaUI.tbProductos, ProcesoListado.listarDatos("boleta"));
        modeloProducto = (DefaultTableModel) VentaUI.tbProductos.getModel();

        textFieldComboCliente = (JTextField) VentaUI.cbCliente.getEditor().getEditorComponent();
        textFieldComboProducto = (JTextField) VentaUI.cbProducto.getEditor().getEditorComponent();
        textFieldComboServicio = (JTextField) VentaUI.cbServicio.getEditor().getEditorComponent();
        llenarCboClientes();
        llenarCboProducto();
        llenarCboServicio();

        String cod = ProcesoListado.generarCodigoUnico("boleta", "id_boleta", "BOL-", 4);
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

    @Override
    protected void addListeners() {
        VentaUI.btnRegistrar.addActionListener(this);
        VentaUI.btnBuscar.addActionListener(this);
        VentaUI.btnModificar.addActionListener(this);
        VentaUI.btnEliminar.addActionListener(this);
        VentaUI.btnAgregar.addActionListener(this);

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
        UI_ReporteVentaController controllerVenta = new UI_ReporteVentaController(VentaUI, vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == VentaUI.btnAgregar) {
            if (VentaUI.cbCliente.getSelectedIndex() != 0) {
                if (VentaUI.cbProducto.getSelectedIndex() != 0 || VentaUI.cbServicio.getSelectedIndex() != 0) {
                    ProcesoListado.insertarEnTabla(VentaUI.tbProductos, ProcesoInsert.obtenerItem(VentaUI));
                    double total = sumaColumna();
                    double impuesto = total * 0.18;
                    double importeFinal = total - impuesto;
                    VentaUI.lblTotal.setText("S/." + total);
                    VentaUI.lblImpuesto.setText("S/." + impuesto);
                    VentaUI.lblTotalFinal.setText("S/." + importeFinal);
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
            } else {
                VentaUI.btnRegistrar.setText("Nuevo");
                VentaUI.btnAgregar.setEnabled(false);
                VentaUI.cbCliente.setEnabled(false);
                VentaUI.lblTotal.setText("S/.00.00");
                VentaUI.lblImpuesto.setText("S/.00.00");
                VentaUI.lblTotalFinal.setText("S/.00.00");
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
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
