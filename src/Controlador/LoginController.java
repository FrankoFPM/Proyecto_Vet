package Controlador;

import static DAO.MetodosReadDelete.Autenticar;

import Vista.Login;
import Vista.Dashboard_UI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    Login vLogin;
    Dashboard_UI dashboard = new Dashboard_UI();

    public LoginController(Login vLogin) {
        this.vLogin = vLogin;
        this.vLogin.btnIngresar.addActionListener(this);
        vLogin.txtUser.putClientProperty("JTextField.placeholderText", "Ingresa tu usuario");
        vLogin.txtPass.putClientProperty("JTextField.placeholderText", "Ingresa tu contraseña");
        Run();
    }

    void Run() {
        vLogin.setLocationRelativeTo(null);
        vLogin.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
        vLogin.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(23, 180, 252));
        vLogin.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vLogin.btnIngresar) {
            System.out.println("login");
            Autenticar(vLogin, dashboard);
        }
    }
}
