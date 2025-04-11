package modelo;

/**
 * Clase que representa un libro en el inventario
 */
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private double precio;
    private int stock;
    private int categoriaId;
    private String categoriaNombre; // Para mostrar en listados
    
    /**
     * Constructor vacío
     */
    public Libro() {
    }
    
    /**
     * Constructor sin ID (para inserción)
     */
    public Libro(String titulo, String autor, String isbn, double precio, int stock, int categoriaId) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = categoriaId;
    }
    
    /**
     * Constructor completo
     */
    public Libro(int id, String titulo, String autor, String isbn, double precio, int stock, int categoriaId) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = categoriaId;
    }
    
    // Getters y setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public int getCategoriaId() {
        return categoriaId;
    }
    
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
    
    public String getCategoriaNombre() {
        return categoriaNombre;
    }
    
    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Autor: %s | ISBN: %s | Precio: %.2f | Stock: %d | Categoría: %s",
                id, titulo, autor, isbn, precio, stock, categoriaNombre != null ? categoriaNombre : "Sin categoría");
    }
}
