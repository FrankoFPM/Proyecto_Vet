package Controlador;

import Vista.Dashboard_UI;
import Vista.Home_UI;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UI_HomeController extends PanelController implements MouseListener {

    Home_UI homeUI;

    public UI_HomeController(Home_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.homeUI = panel;
        super.showWindow(panel);
        addListeners();
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
            System.out.println("usuarios");
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

}
