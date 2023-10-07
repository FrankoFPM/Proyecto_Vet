package Controlador;

import Modelo.PersonaCliente;
import Procesos.ProcesoInsert;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;
import Procesos.ProcesoUpdate;
import Procesos.ProcesoValidacion;
import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UI_ClienteController extends PanelController implements ActionListener {

    Cliente_UI ClienteUI;
    PersonaCliente cliente;

    String titutos[] = {"COD", "Nombre", "Apellidos", "Telefono",
        "Correo", "DNI", "Direccion"};

    String msgCliente[] = {"Nombres", "Apellidos", "Telefono",
        "Correo", "DNI", "Direccion"};
    JTextField txtCliente[];

    public UI_ClienteController(Cliente_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.ClienteUI = panel;
        txtCliente = new JTextField[]{ClienteUI.txtNombres, ClienteUI.txtApellidos,
            ClienteUI.txtTelefono, ClienteUI.txtCorreo, ClienteUI.txtDni, ClienteUI.txtDireccion
        };
        ProcesoValidacion.placeholderJtxt(txtCliente, msgCliente);
        ProcesoListado.tituloTabla(ClienteUI.tbClientes, titutos);
        ProcesoListado.llenarTabla(ClienteUI.tbClientes, ProcesoListado.listarDatos("cliente"));

        super.showWindow(panel);

        addListeners();

        String cod = ProcesoListado.generarCodigo("cliente", "id_cliente", "CLI-", 4);
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
            ProcesoRD.eliminarRegistros("cliente", "id_cliente", ClienteUI.lblCodigo.getText());
            ClienteUI.btnBuscar.setText("Buscar");
            ClienteUI.btnEliminar.setEnabled(false);
            ClienteUI.btnModificar.setEnabled(false);
            Cliente_UI newcliente = new Cliente_UI();
            UI_ClienteController controllerCliente = new UI_ClienteController(newcliente, DashboardController.vista);
        } else if (e.getSource() == ClienteUI.btnRegistrar) {
            if (ProcesoValidacion.validarInputs(txtCliente, msgCliente)) {
                PersonaCliente cliente = new PersonaCliente();
                cliente.setCodigo(ClienteUI.lblCodigo.getText());
                cliente.setNombre(txtCliente[0].getText());
                cliente.setApellido(txtCliente[1].getText());
                cliente.setTelefono(txtCliente[2].getText());
                cliente.setCorreo(txtCliente[3].getText());
                cliente.setDni(Integer.parseInt(txtCliente[4].getText()));
                cliente.setDireccion(txtCliente[5].getText());
                ProcesoInsert.insertarCliente(cliente);
                Cliente_UI newcliente = new Cliente_UI();
                UI_ClienteController controllerCliente = new UI_ClienteController(newcliente, DashboardController.vista);
            }
        } else if (e.getSource() == ClienteUI.btnBuscar) {
            if (ClienteUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el DNI del cliente");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        String[] datos = ProcesoRD.buscarRegistros("cliente", "dni", dato);
                        if (datos.length != 0) {
                            ClienteUI.lblCodigo.setText(datos[0]);
                            for (int i = 0; i < txtCliente.length; i++) {
                                txtCliente[i].setText(datos[i + 1]);
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
                Cliente_UI newcliente = new Cliente_UI();
                UI_ClienteController controllerCliente = new UI_ClienteController(newcliente, DashboardController.vista);
            }
        } else if (e.getSource() == ClienteUI.btnModificar) {
            if (ProcesoValidacion.validarInputs(txtCliente, msgCliente)) {
                PersonaCliente cliente = new PersonaCliente();
                cliente.setCodigo(ClienteUI.lblCodigo.getText());
                cliente.setNombre(txtCliente[0].getText());
                cliente.setApellido(txtCliente[1].getText());
                cliente.setTelefono(txtCliente[2].getText());
                cliente.setCorreo(txtCliente[3].getText());
                cliente.setDni(Integer.parseInt(txtCliente[4].getText()));
                cliente.setDireccion(txtCliente[5].getText());
                ProcesoUpdate.actualizarCliente(cliente);
                Cliente_UI newcliente = new Cliente_UI();
                UI_ClienteController controllerCliente = new UI_ClienteController(newcliente, DashboardController.vista);
            }
        }
    }
}
