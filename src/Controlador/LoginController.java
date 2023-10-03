package Controlador;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import Vista.Login;
import Vista.Dashboard_UI;
import DB.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

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

    public static void Autenticar(Login lg, Dashboard_UI dash) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_Login;

        String user = lg.txtUser.getText();
        char[] passChars = lg.txtPass.getPassword();
        String pass = new String(passChars);

        try {
            String query = "{call ValidarCredenciales(?,?,?)}";
            cs_Login = cn.prepareCall(query);
            cs_Login.setString(1, user);
            cs_Login.setString(2, pass);
            cs_Login.registerOutParameter(3, Types.BOOLEAN);
            cs_Login.execute();
            boolean resultado = cs_Login.getBoolean(3);
            cs_Login.close();
            if (resultado) {
                JOptionPane.showMessageDialog(lg, "Inicio de sesión exitoso", "Success", JOptionPane.INFORMATION_MESSAGE);
                lg.dispose();
                //dash.labelUsername.setText("Bienvenido, " + user.toUpperCase());
                DashboardController controllerDash = new DashboardController(dash, user.toUpperCase());
                /*dash.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
                dash.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(23, 180, 252));
                dash.setVisible(true);*/
            } else {
                JOptionPane.showMessageDialog(lg, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
            objConn.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vLogin.btnIngresar) {
            System.out.println("login");
            Autenticar(vLogin, dashboard);
        }
    }
}
