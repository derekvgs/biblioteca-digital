package conexion;

// import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que maneja la conexión a la base de datos
 */
public class Conexion {
    private static Connection conexion = null;
    // private static final String PROPERTIES_FILE = "config/db.properties";
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Objeto Connection con la conexión activa
     * @throws SQLException Si hay un error en la conexión
     */
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Properties props = new Properties();
                props.load(Conexion.class.getClassLoader().getResourceAsStream("config/db.properties"));
                
                String host = props.getProperty("db.host");
                String port = props.getProperty("db.port");
                String dbName = props.getProperty("db.name");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                
                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
                
                // Registrar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establecer la conexión
                conexion = DriverManager.getConnection(url, user, password);
            } catch (IOException e) {
                System.err.println("Error al leer el archivo de propiedades: " + e.getMessage());
                throw new SQLException("No se pudo configurar la conexión a la base de datos");
            } catch (ClassNotFoundException e) {
                System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
                throw new SQLException("No se pudo cargar el driver de MySQL");
            }
        }
        return conexion;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}