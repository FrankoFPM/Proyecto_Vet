package Controlador;

import Vista.Cita_UI;
import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import Vista.Home_UI;
import Vista.Inventario_UI;
import Vista.Paciente_UI;
import Vista.Personal_UI;
import Vista.RPClinico_UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class DashboardController implements ActionListener {

    public static Dashboard_UI vista;
    String user;
    //panels
    Home_UI home = null;
    Cliente_UI cliente = null;
    Paciente_UI paciente = null;
    Personal_UI personal = null;
    Cita_UI cita = null;
    RPClinico_UI rpClinico = null;
    Inventario_UI inventario = null;
    //model
    DefaultTableModel modeloCita;

    public DashboardController(Dashboard_UI dash, String user) {
        this.vista = dash;
        this.user = user;
        vista.btnHome.addActionListener(this);
        vista.btnCliente.addActionListener(this);
        vista.btnPaciente.addActionListener(this);
        vista.btnCita.addActionListener(this);
        vista.btnPersonal.addActionListener(this);
        vista.btnInventario.addActionListener(this);
        vista.btnRpClinico.addActionListener(this);
        vista.btnRpVentas.addActionListener(this);
        launchApp();
    }

    void launchApp() {
        vista.labelUsername.setText("Bienvenido, " + user.toUpperCase());
        vista.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
        vista.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(23, 180, 252));
        vista.setLocationRelativeTo(null);
        //ChangePanel(home);
        showHome();
        vista.setVisible(true);
    }

    void showHome() {
        home = new Home_UI();
        UI_HomeController controllerHome = new UI_HomeController(home, vista);
    }

    public void ChangePanel(JPanel box) {
        box.setPreferredSize(new Dimension(1000, 500)); // Tamaño inicial

        vista.content.removeAll();
        vista.content.setLayout(new BorderLayout());
        vista.content.add(box, BorderLayout.CENTER);
        vista.content.revalidate();
        vista.content.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnHome) {
            System.out.println("Home");
            showHome();
        } else if (e.getSource() == vista.btnCliente) {
            cliente = new Cliente_UI();
            UI_ClienteController controllerCliente = new UI_ClienteController(cliente, vista);
        } else if (e.getSource() == vista.btnPaciente) {
            paciente = new Paciente_UI();
            UI_PacienteController controllerPaciente = new UI_PacienteController(paciente, vista);
        }else if (e.getSource() == vista.btnPersonal) {
            personal = new Personal_UI();
            UI_PersonalController controllerPersonal = new UI_PersonalController(personal, vista);
        }else if (e.getSource() == vista.btnCita) {
            cita = new Cita_UI();
            UI_CitaController controllerCita = new UI_CitaController(cita, vista);
        }else if (e.getSource() == vista.btnRpClinico) {
            rpClinico = new RPClinico_UI();
            UI_ReporteClinicoController controllerRPclinico = new UI_ReporteClinicoController(rpClinico, vista);
        }else if (e.getSource() == vista.btnInventario) {
            inventario = new Inventario_UI();
            UI_InventarioController controllerRPclinico = new UI_InventarioController(inventario, vista);
        }
    }

}
