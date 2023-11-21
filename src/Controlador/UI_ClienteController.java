package Controlador;

import static Controlador.DashboardController.vista;
import DAO.DAOCliente;
import Modelo.PersonaCliente;
import DAO.MetodosList;
import DataUtils.ProcesoValidacion;
import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UI_ClienteController extends PanelController implements ActionListener {

    private DAOCliente clienteDAO;
    Cliente_UI ClienteUI;

    String titutos[] = { "COD", "Nombre", "Apellidos", "Telefono",
            "Correo", "DNI", "Direccion" };

    String msgCliente[] = { "Nombres", "Apellidos", "Telefono",
            "Correo", "DNI", "Direccion" };
    JTextField txtCliente[];

    public UI_ClienteController(Cliente_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.ClienteUI = panel;
        txtCliente = new JTextField[] { ClienteUI.txtNombres, ClienteUI.txtApellidos,
                ClienteUI.txtTelefono, ClienteUI.txtCorreo, ClienteUI.txtDni, ClienteUI.txtDireccion
        };
        ProcesoValidacion.placeholderJtxt(txtCliente, msgCliente);
        MetodosList.tituloTabla(ClienteUI.tbClientes, titutos);
        clienteDAO = new DAOCliente();
        MetodosList.llenarTabla(ClienteUI.tbClientes, clienteDAO.listarClientes());

        // super.showWindow(panel);
        addListeners();

        String cod = MetodosList.generarCodigo("cliente", "id_cliente", "CLI-", 4);
        ClienteUI.lblCodigo.setText(cod);
        ClienteUI.btnEliminar.setEnabled(false);
        ClienteUI.btnModificar.setEnabled(false);
    }

    @Override
    protected void addListeners() {
        ClienteUI.btnEliminar.addActionListener(this);
        ClienteUI.btnRegistrar.addActionListener(this);
        ClienteUI.btnBuscar.addActionListener(this);
        ClienteUI.btnModificar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ClienteUI.btnEliminar) {
            clienteDAO = new DAOCliente();
            clienteDAO.eliminarCliente(ClienteUI.lblCodigo.getText());
            ClienteUI.btnBuscar.setText("Buscar");
            ClienteUI.btnEliminar.setEnabled(false);
            ClienteUI.btnModificar.setEnabled(false);
            reloadWindow();
        } else if (e.getSource() == ClienteUI.btnRegistrar) {
            if (ProcesoValidacion.validarInputs(txtCliente, msgCliente)) {
                PersonaCliente cliente = new PersonaCliente();
                cliente.setCodigo(ClienteUI.lblCodigo.getText());
                cliente.setNombre(txtCliente[0].getText());
                cliente.setApellido(txtCliente[1].getText());
                cliente.setTelefono(txtCliente[2].getText());
                cliente.setCorreo(txtCliente[3].getText());
                String dniText = txtCliente[4].getText();
                if (!dniText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "El DNI solo debe contener números");
                    return;
                }
                cliente.setDni(Integer.parseInt(dniText));
                cliente.setDireccion(txtCliente[5].getText());

                if (!ProcesoValidacion.validarEmail(cliente.getCorreo()) ||
                        !ProcesoValidacion.validarTelefono(cliente.getTelefono()) ||
                        !ProcesoValidacion.validarDni(cliente.getDni())) {
                    return;
                }

                clienteDAO = new DAOCliente();
                clienteDAO.insertarCliente(cliente);
                reloadWindow();
            }
        } else if (e.getSource() == ClienteUI.btnBuscar) {
            if (ClienteUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el DNI del cliente");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        clienteDAO = new DAOCliente();
                        List<String[]> datos = clienteDAO.buscarCliente(dato);
                        if (!datos.isEmpty()) {
                            String[] primerRegistro = datos.get(0);
                            ClienteUI.lblCodigo.setText(primerRegistro[0]);
                            for (int i = 0; i < txtCliente.length; i++) {
                                txtCliente[i].setText(primerRegistro[i + 1]);
                            }
                            ClienteUI.btnEliminar.setEnabled(true);
                            ClienteUI.btnModificar.setEnabled(true);
                            ClienteUI.btnBuscar.setText("Cancelar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No ingresó ningún dato");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada");
                }
            } else {
                ClienteUI.btnBuscar.setText("Buscar");
                ClienteUI.btnEliminar.setEnabled(false);
                ClienteUI.btnModificar.setEnabled(false);
                reloadWindow();
            }
        } else if (e.getSource() == ClienteUI.btnModificar) {
            if (ProcesoValidacion.validarInputs(txtCliente, msgCliente)) {
                PersonaCliente cliente = new PersonaCliente();
                cliente.setCodigo(ClienteUI.lblCodigo.getText());
                cliente.setNombre(txtCliente[0].getText());
                cliente.setApellido(txtCliente[1].getText());
                cliente.setTelefono(txtCliente[2].getText());
                cliente.setCorreo(txtCliente[3].getText());
                String dniText = txtCliente[4].getText();
                if (!dniText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "El DNI solo debe contener números", "Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                cliente.setDni(Integer.parseInt(dniText));
                cliente.setDireccion(txtCliente[5].getText());
                if (!ProcesoValidacion.validarEmail(cliente.getCorreo()) ||
                        !ProcesoValidacion.validarTelefono(cliente.getTelefono()) ||
                        !ProcesoValidacion.validarDni(cliente.getDni())) {
                    return;
                }
                clienteDAO = new DAOCliente();
                clienteDAO.actualizarCliente(cliente);
                reloadWindow();
            }
        }
    }

    @Override
    protected void reloadWindow() {
        Cliente_UI newcliente = new Cliente_UI();
        UI_ClienteController controllerCliente = new UI_ClienteController(newcliente, vista);
    }
}
