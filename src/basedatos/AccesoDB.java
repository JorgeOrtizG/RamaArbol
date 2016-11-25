package basedatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {

    static Connection cn = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        
        try {
            //cargar driver en memoria
            Class.forName("com.mysql.jdbc.Driver");
            // url de la bd
            String url = "jdbc:mysql://localhost:3306/unac101";
            // obtener la conexion a bd
            cn = DriverManager.getConnection(url, "root", "marina");
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
        return cn;
    }
    
    
  
}

