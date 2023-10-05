package Controlador;

import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import Vista.Home_UI;
import Vista.Paciente_UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class DashboardController implements ActionListener {

    Dashboard_UI vista;
    String user;
    //panels
    Home_UI home = null;
    Cliente_UI cliente = null;
    Paciente_UI paciente = null;
    //model
    DefaultTableModel modeloCita;

    public DashboardController(Dashboard_UI dash, String user) {
        this.vista = dash;
        this.user = user;
        vista.btnHome.addActionListener(this);
        vista.btnCliente.addActionListener(this);
        vista.btnPaciente.addActionListener(this);
        vista.btnCita.addActionListener(this);
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

    void ChangePanel(JPanel box) {
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
            //ChangePanel(home);
            showHome();
        } else if (e.getSource() == vista.btnCliente) {
            cliente = new Cliente_UI();
            //cliente.txtNombres.putClientProperty("JTextField.placeholderText", "Ingresar ");
            UI_ClienteController controllerCliente = new UI_ClienteController(cliente, vista);
        } else if (e.getSource() == vista.btnPaciente) {
            paciente = new Paciente_UI();
            UI_PacienteController controllerPaciente = new UI_PacienteController(paciente, vista);
        }
    }

}
