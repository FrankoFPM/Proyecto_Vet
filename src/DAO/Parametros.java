package DAO;

public interface Parametros {

    String usuario = "root";
    String pass = "";
    String puerto = "3306";
    String db = "vet";
    String ip = "localhost";
    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + db;
}
