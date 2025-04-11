package modelo;

/**
 * Clase que representa una categoría de libros
 */
public class Categoria {
    private int id;
    private String nombre;
    
    /**
     * Constructor vacío
     */
    public Categoria() {
    }
    
    /**
     * Constructor con nombre
     * @param nombre Nombre de la categoría
     */
    public Categoria(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Constructor completo
     * @param id ID de la categoría
     * @param nombre Nombre de la categoría
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    // Getters y setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
