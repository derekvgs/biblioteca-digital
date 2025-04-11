package dao;

import conexion.Conexion;
import modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de base de datos para libros
 */
public class LibroDAO {
    
    /**
     * Inserta un nuevo libro en la base de datos
     * @param libro Objeto Libro a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, precio, stock, categoria_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setDouble(4, libro.getPrecio());
            stmt.setInt(5, libro.getStock());
            
            if (libro.getCategoriaId() > 0) {
                stmt.setInt(6, libro.getCategoriaId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza un libro existente
     * @param libro Objeto Libro con los nuevos datos
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, precio = ?, stock = ?, categoria_id = ? WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setDouble(4, libro.getPrecio());
            stmt.setInt(5, libro.getStock());
            
            if (libro.getCategoriaId() > 0) {
                stmt.setInt(6, libro.getCategoriaId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }
            
            stmt.setInt(7, libro.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un libro por su ID
     * @param id ID del libro a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza el stock de un libro
     * @param id ID del libro
     * @param nuevoStock Nueva cantidad de stock
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean actualizarStock(int id, int nuevoStock) {
        String sql = "UPDATE libros SET stock = ? WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca un libro por su ID
     * @param id ID del libro a buscar
     * @return Objeto Libro si se encuentra, null en caso contrario
     */
    public Libro obtenerPorId(int id) {
        String sql = "SELECT l.*, c.nombre as categoria_nombre FROM libros l " +
                     "LEFT JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return crearLibroDeResultSet(rs);
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Error al obtener libro: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca un libro por su ISBN
     * @param isbn ISBN del libro a buscar
     * @return Objeto Libro si se encuentra, null en caso contrario
     */
    public Libro obtenerPorIsbn(String isbn) {
        String sql = "SELECT l.*, c.nombre as categoria_nombre FROM libros l " +
                     "LEFT JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.isbn = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, isbn);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return crearLibroDeResultSet(rs);
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Error al obtener libro por ISBN: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Verifica si existe un libro con el ISBN especificado
     * @param isbn ISBN a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeIsbn(String isbn) {
        String sql = "SELECT COUNT(*) FROM libros WHERE isbn = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, isbn);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Error al verificar ISBN: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene todos los libros
     * @return Lista de objetos Libro
     */
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, c.nombre as categoria_nombre FROM libros l " +
                     "LEFT JOIN categorias c ON l.categoria_id = c.id " +
                     "ORDER BY l.titulo";
        
        try (Connection conn = Conexion.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(crearLibroDeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener libros: " + e.getMessage());
        }
        
        return libros;
    }
    
    /**
     * Busca libros por título
     * @param titulo Texto a buscar en el título
     * @return Lista de objetos Libro que coinciden con la búsqueda
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, c.nombre as categoria_nombre FROM libros l " +
                     "LEFT JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.titulo LIKE ? " +
                     "ORDER BY l.titulo";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                libros.add(crearLibroDeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar libros por título: " + e.getMessage());
        }
        
        return libros;
    }
    
    /**
     * Busca libros por categoría
     * @param categoriaId ID de la categoría
     * @return Lista de objetos Libro en esa categoría
     */
    public List<Libro> buscarPorCategoria(int categoriaId) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, c.nombre as categoria_nombre FROM libros l " +
                     "LEFT JOIN categorias c ON l.categoria_id = c.id " +
                     "WHERE l.categoria_id = ? " +
                     "ORDER BY l.titulo";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoriaId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                libros.add(crearLibroDeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar libros por categoría: " + e.getMessage());
        }
        
        return libros;
    }
    
    /**
     * Crea un objeto Libro a partir de un ResultSet
     * @param rs ResultSet con datos del libro
     * @return Objeto Libro con los datos cargados
     * @throws SQLException Si hay un error al acceder a los datos
     */
    private Libro crearLibroDeResultSet(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setId(rs.getInt("id"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setPrecio(rs.getDouble("precio"));
        libro.setStock(rs.getInt("stock"));
        
        // El campo categoria_id puede ser NULL
        Object categoriaId = rs.getObject("categoria_id");
        if (categoriaId != null) {
            libro.setCategoriaId(rs.getInt("categoria_id"));
        }
        
        libro.setCategoriaNombre(rs.getString("categoria_nombre"));
        
        return libro;
    }
}
