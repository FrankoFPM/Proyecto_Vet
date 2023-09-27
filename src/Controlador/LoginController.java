package Controlador;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import Vista.Login;
import Vista.Dashboard_UI;
import DB.Conexion;
import javax.swing.JOptionPane;

public class LoginController {

    public static void Autenticar(Login lg, Dashboard_UI dash) {
        Conexion objConn = new Conexion();
        Connection cn = objConn.ObtenerConexion();
        CallableStatement cs_Login = null;

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
                JOptionPane.showMessageDialog(dash, "Inicio de sesión exitoso");
                lg.dispose();
                dash.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(dash, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage());
        }
    }
}
