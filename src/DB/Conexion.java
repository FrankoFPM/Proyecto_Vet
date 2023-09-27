package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    Connection conectar = null;
    String usuario = "root";
    String pass = "";
    String puerto = "3306";
    String db = "vet";
    String ip = "localhost";
    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + db;

    //conexion
    public Connection ObtenerConexion() {
        try {
            //registrar driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, pass);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error cn: " + e.getMessage());
        }
        return conectar;
    }

    public void CerrarConexion() {
        try {
            conectar.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
