package xyz.mendesoft.jbtienda.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johan
 */
public class PSQLConexion {

    private final String URL = "jdbc:postgresql://localhost:5432/dbjbtienda";
    private final String USER = "postgres";
    private final String PASSWORD = "Master123";

    public Connection conectar() {
        Connection conexion = null;

        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqle) {
            System.out.println("eror de conexion: " + sqle);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PSQLConexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conexion;
    }
    
        // Método principal para probar la conexión
    public static void main(String[] args) {
        PSQLConexion conexion = new PSQLConexion();
        Connection conn = conexion.conectar();
        
        if (conn != null) {
            // Si la conexión es exitosa
            System.out.println("Conexión exitosa a la base de datos");
        } else {
            // Si falla la conexión
            System.out.println("Error al conectarse a la base de datos");
        }
    }

}
