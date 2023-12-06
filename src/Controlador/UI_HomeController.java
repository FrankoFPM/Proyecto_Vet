package Controlador;

import static Controlador.DashboardController.role;
import static Controlador.UI_CitaController.titulosCitas;
import DAO.MetodosList;
import Modelo.Cita;
import Reportes.Reportes;
import Vista.Cita_UI;
import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import Vista.Home_UI;
import Vista.Paciente_UI;
import Vista.RPVenta_UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UI_HomeController extends PanelController implements MouseListener {

    Home_UI homeUI;
    // panels
    // Home_UI home = null;
    Cliente_UI cliente = null;
    Paciente_UI paciente = null;
    Cita_UI cita = null;
    RPVenta_UI rpVenta = null;

    public UI_HomeController(Home_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.homeUI = panel;
        super.showWindow(panel);
        addListeners();

        MetodosList.tituloTabla(homeUI.tbCitasProximas, titulosCitas);
        MetodosList.llenarTabla(homeUI.tbCitasProximas, MetodosList.listarCitasProximas());

        homeUI.lblUsuarios.setText(String.valueOf(MetodosList.contarClientes()));
        homeUI.lblCitas.setText(String.valueOf(MetodosList.contarCitasProgramadas()));
        homeUI.lblIngresos.setText("S/." + String.valueOf(MetodosList.sumarIngresos()));
    }

    @Override
    protected void addListeners() {
        homeUI.btnlblCitas.addMouseListener(this);
        homeUI.btnlblIngresos.addMouseListener(this);
        homeUI.btnlblUsuarios.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println(e.getSource());
        if (e.getSource() == homeUI.btnlblCitas && role.equals("Administrador")) {
            cita = new Cita_UI();
            UI_CitaController controllerCita = new UI_CitaController(cita, DashboardController.vista);
        } else if (e.getSource() == homeUI.btnlblIngresos && role.equals("Administrador")) {
            rpVenta = new RPVenta_UI();
            UI_ReporteVentaController controllerRPVenta = new UI_ReporteVentaController(rpVenta,
                    DashboardController.vista);
        } else if (e.getSource() == homeUI.btnlblUsuarios && role.equals("Administrador")) {
            cliente = new Cliente_UI();
            UI_ClienteController controllerCliente = new UI_ClienteController(cliente, DashboardController.vista);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    protected void reloadWindow() {
    }

}
