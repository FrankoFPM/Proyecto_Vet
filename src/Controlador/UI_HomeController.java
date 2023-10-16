package Controlador;

import static Controlador.UI_CitaController.titulosCitas;
import Procesos.ProcesoListado;
import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import Vista.Home_UI;
import Vista.Paciente_UI;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UI_HomeController extends PanelController implements MouseListener {

    Home_UI homeUI;
    //panels
    //Home_UI home = null;
    Cliente_UI cliente = null;
    Paciente_UI paciente = null;

    public UI_HomeController(Home_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.homeUI = panel;
        super.showWindow(panel);
        addListeners();
        
        ProcesoListado.tituloTabla(homeUI.tbCitasProximas, titulosCitas);
        ProcesoListado.llenarTabla(homeUI.tbCitasProximas, ProcesoListado.listarCitasProximas());

        homeUI.lblUsuarios.setText(String.valueOf(ProcesoListado.contarClientes()));
        homeUI.lblCitas.setText(String.valueOf(ProcesoListado.contarCitasProgramadas()));
        homeUI.lblIngresos.setText("S/."+String.valueOf(ProcesoListado.sumarIngresos()));
    }

    @Override
    protected void addListeners() {
        homeUI.btnlblCitas.addMouseListener(this);
        homeUI.btnlblIngresos.addMouseListener(this);
        homeUI.btnlblUsuarios.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println(e.getSource());
        if (e.getSource() == homeUI.btnlblCitas) {
            System.out.println("cita");
        } else if (e.getSource() == homeUI.btnlblIngresos) {
            System.out.println("ingreso");
        } else if (e.getSource() == homeUI.btnlblUsuarios) {
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
