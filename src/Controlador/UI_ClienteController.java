package Controlador;

import Modelo.PersonaCliente;
import Procesos.ProcesoListado;
import Procesos.ProcesoValidacion;
import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    @Override
    protected void addListeners() {
        ClienteUI.btnEliminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ClienteUI.btnEliminar) {
            System.out.println("eliminar");
            ProcesoValidacion.validarInputs(txtCliente, msgCliente);
        }
    }
}
