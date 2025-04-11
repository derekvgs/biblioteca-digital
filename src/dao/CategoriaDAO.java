package dao;

import conexion.Conexion;
import modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de base de datos para categorías
 */
public class CategoriaDAO {
    
    /**
     * Inserta una nueva categoría en la base de datos
     * @param categoria Objeto Categoria a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean insertar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, categoria.getNombre());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    categoria.setId(rs.getInt(1));
                }
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza una categoría existente
     * @param categoria Objeto Categoria con los nuevos datos
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar categoría: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una categoría por su ID
     * @param id ID de la categoría a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca una categoría por su ID
     * @param id ID de la categoría a buscar
     * @return Objeto Categoria si se encuentra, null en caso contrario
     */
    public Categoria obtenerPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                return categoria;
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Error al obtener categoría: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene todas las categorías
     * @return Lista de objetos Categoria
     */
    public List<Categoria> obtenerTodos() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY nombre";
        
        try (Connection conn = Conexion.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categorias.add(categoria);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
        }
        
        return categorias;
    }
    
    /**
     * Verifica si existe una categoría con el nombre especificado
     * @param nombre Nombre de la categoría a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM categorias WHERE nombre = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Error al verificar categoría: " + e.getMessage());
            return false;
        }
    }
}
