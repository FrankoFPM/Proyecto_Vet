package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.PersonaCliente;
import Modelo.Producto;
import Procesos.ProcesoListado;
import Vista.Dashboard_UI;
import Vista.RPVenta_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UI_ReporteVenta extends PanelController implements ActionListener, ListSelectionListener, FocusListener {

    RPVenta_UI VentaUI;

    JTextField textFieldComboCliente;
    JTextField textFieldComboProducto;
    JTextField textFieldComboServicio;

    public UI_ReporteVenta(RPVenta_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.VentaUI = panel;

        VentaUI.datePicker.setDateToToday();
        VentaUI.timePicker.setTimeToNow();

        textFieldComboCliente = (JTextField) VentaUI.cbCliente.getEditor().getEditorComponent();
        textFieldComboProducto = (JTextField) VentaUI.cbProducto.getEditor().getEditorComponent();
        textFieldComboServicio = (JTextField) VentaUI.cbServicio.getEditor().getEditorComponent();
        llenarCboClientes();
        llenarCboProducto();
        llenarCboServicio();

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

        ArrayList<Producto> listaProductos = ProcesoListado.obtenerProducto();
        for (int i = 0; i < listaProductos.size(); i++) {
            VentaUI.cbProducto.addItem(new Producto(listaProductos.get(i).getCodigo(),
                    listaProductos.get(i).getInfo()));
        }
    }

    private void llenarCboServicio() {
        VentaUI.cbServicio.removeAllItems();

        ArrayList<Producto> listaProductos = ProcesoListado.obtenerServicios();
        for (int i = 0; i < listaProductos.size(); i++) {
            VentaUI.cbServicio.addItem(new Producto(listaProductos.get(i).getCodigo(),
                    listaProductos.get(i).getInfo(),
                    listaProductos.get(i).getMarca())
            );
        }
    }

    @Override
    protected void addListeners() {
        VentaUI.btnRegistrar.addActionListener(this);
        VentaUI.btnBuscar.addActionListener(this);
        VentaUI.btnModificar.addActionListener(this);
        VentaUI.btnEliminar.addActionListener(this);
        VentaUI.btnAgregar.addActionListener(this);

        VentaUI.tbProductos.getSelectionModel().addListSelectionListener(this);

        textFieldComboCliente.addFocusListener(this);
        textFieldComboProducto.addFocusListener(this);
        textFieldComboServicio.addFocusListener(this);
    }

    @Override
    protected void reloadWindow() {
        RPVenta_UI newVenta = new RPVenta_UI();
        UI_ReporteVenta controllerVenta = new  UI_ReporteVenta(VentaUI, vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == VentaUI.btnAgregar) {
            if (VentaUI.cbCliente.getSelectedIndex() != 0) {
                
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
            for (Producto productos : ProcesoListado.obtenerProducto()) {
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
            for (Producto servicios : ProcesoListado.obtenerServicios()) {
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

}
