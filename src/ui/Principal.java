package ui;

import conexion.Conexion;
import dao.CategoriaDAO;
import dao.LibroDAO;
import modelo.Categoria;
import modelo.Libro;
import util.Validador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que maneja la interfaz de usuario en consola
 */
public class Principal {
    private static Scanner scanner = new Scanner(System.in);
    private static LibroDAO libroDAO = new LibroDAO();
    private static CategoriaDAO categoriaDAO = new CategoriaDAO();
    
    public static void main(String[] args) {
        boolean salir = false;
        
        // Probar la conexión a la base de datos
        try {
            Connection conn = Conexion.obtenerConexion();
            System.out.println("Conexión establecida correctamente con la base de datos.");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            System.err.println("Verifique que MySQL esté ejecutándose y que la base de datos esté creada.");
            return;
        }
        
        System.out.println("=================================================");
        System.out.println("  SISTEMA DE GESTIÓN DE BIBLIOTECA 'LETRAS Y PÁGINAS'");
        System.out.println("=================================================");
        
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    menuLibros();
                    break;
                case 2:
                    menuCategorias();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        
        // Cerrar recursos
        scanner.close();
        Conexion.cerrarConexion();
    }
    
    /**
     * Muestra el menú principal
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestión de Libros");
        System.out.println("2. Gestión de Categorías");
        System.out.println("0. Salir");
    }
    
    /**
     * Muestra y gestiona el menú de libros
     */
    private static void menuLibros() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE LIBROS ---");
            System.out.println("1. Registrar nuevo libro");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Buscar libro por ISBN");
            System.out.println("4. Buscar libros por título");
            System.out.println("5. Buscar libros por categoría");
            System.out.println("6. Actualizar libro");
            System.out.println("7. Actualizar stock");
            System.out.println("8. Eliminar libro");
            System.out.println("0. Volver al menú principal");
            
            int opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    registrarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    buscarPorIsbn();
                    break;
                case 4:
                    buscarPorTitulo();
                    break;
                case 5:
                    buscarPorCategoria();
                    break;
                case 6:
                    actualizarLibro();
                    break;
                case 7:
                    actualizarStock();
                    break;
                case 8:
                    eliminarLibro();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
    /**
     * Muestra y gestiona el menú de categorías
     */
    private static void menuCategorias() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Crear nueva categoría");
            System.out.println("2. Listar todas las categorías");
            System.out.println("3. Actualizar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Volver al menú principal");
            
            int opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    crearCategoria();
                    break;
                case 2:
                    listarCategorias();
                    break;
                case 3:
                    actualizarCategoria();
                    break;
                case 4:
                    eliminarCategoria();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
    // ========== MÉTODOS PARA GESTIÓN DE LIBROS ==========
    
    /**
     * Registra un nuevo libro
     */
    private static void registrarLibro() {
        System.out.println("\n--- REGISTRAR NUEVO LIBRO ---");
        
        String titulo = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        String isbn = leerIsbn();
        double precio = leerPrecio();
        int stock = leerEnteroPositivo("Cantidad en stock: ");
        
        // Mostrar categorías disponibles
        int categoriaId = seleccionarCategoria();
        
        Libro libro = new Libro(titulo, autor, isbn, precio, stock, categoriaId);
        
        if (libroDAO.insertar(libro)) {
            System.out.println("¡Libro registrado con éxito! ID: " + libro.getId());
        } else {
            System.out.println("Error al registrar el libro.");
        }
    }
    
    /**
     * Lista todos los libros
     */
    private static void listarLibros() {
        System.out.println("\n--- LISTADO DE LIBROS ---");
        List<Libro> libros = libroDAO.obtenerTodos();
        
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            for (Libro libro : libros) {
                System.out.println(libro);
            }
            System.out.println("Total de libros: " + libros.size());
        }
    }
    
    /**
     * Busca un libro por su ISBN
     */
    private static void buscarPorIsbn() {
        System.out.println("\n--- BUSCAR LIBRO POR ISBN ---");
        String isbn = leerTexto("Ingrese el ISBN: ");
        
        Libro libro = libroDAO.obtenerPorIsbn(isbn);
        
        if (libro != null) {
            System.out.println("Libro encontrado:");
            System.out.println(libro);
        } else {
            System.out.println("No se encontró ningún libro con el ISBN: " + isbn);
        }
    }
    
    /**
     * Busca libros por título
     */
    private static void buscarPorTitulo() {
        System.out.println("\n--- BUSCAR LIBROS POR TÍTULO ---");
        String titulo = leerTexto("Ingrese el título o parte del título: ");
        
        List<Libro> libros = libroDAO.buscarPorTitulo(titulo);
        
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros que coincidan con: " + titulo);
        } else {
            System.out.println("Libros encontrados:");
            for (Libro libro : libros) {
                System.out.println(libro);
            }
            System.out.println("Total de libros encontrados: " + libros.size());
        }
    }
    
    /**
     * Busca libros por categoría
     */
    private static void buscarPorCategoria() {
        System.out.println("\n--- BUSCAR LIBROS POR CATEGORÍA ---");
        
        // Mostrar categorías disponibles
        int categoriaId = seleccionarCategoria();
        
        if (categoriaId > 0) {
            List<Libro> libros = libroDAO.buscarPorCategoria(categoriaId);
            
            if (libros.isEmpty()) {
                System.out.println("No hay libros en esta categoría.");
            } else {
                System.out.println("Libros encontrados:");
                for (Libro libro : libros) {
                    System.out.println(libro);
                }
                System.out.println("Total de libros encontrados: " + libros.size());
            }
        }
    }
    
    /**
     * Actualiza los datos de un libro
     */
    private static void actualizarLibro() {
        System.out.println("\n--- ACTUALIZAR LIBRO ---");
        int id = leerEntero("Ingrese el ID del libro a actualizar: ");
        
        Libro libro = libroDAO.obtenerPorId(id);
        
        if (libro != null) {
            System.out.println("Libro encontrado: " + libro.getTitulo());
            System.out.println("Ingrese los nuevos datos (dejar en blanco para mantener el valor actual)");
            
            String titulo = leerTextoOpcional("Título [" + libro.getTitulo() + "]: ");
            if (!titulo.isEmpty()) {
                libro.setTitulo(titulo);
            }
            
            String autor = leerTextoOpcional("Autor [" + libro.getAutor() + "]: ");
            if (!autor.isEmpty()) {
                libro.setAutor(autor);
            }
            
            String isbn = leerTextoOpcional("ISBN [" + libro.getIsbn() + "]: ");
            if (!isbn.isEmpty()) {
                // Verificar si el ISBN ya existe y es diferente al actual
                if (!isbn.equals(libro.getIsbn()) && libroDAO.existeIsbn(isbn)) {
                    System.out.println("Error: El ISBN ya está registrado en otro libro.");
                } else if (Validador.validarIsbn(isbn)) {
                    libro.setIsbn(isbn);
                } else {
                    System.out.println("Error: El ISBN no tiene un formato válido. No se actualizará este campo.");
                }
            }
            
            String precioStr = leerTextoOpcional("Precio [" + libro.getPrecio() + "]: ");
            if (!precioStr.isEmpty()) {
                try {
                    double precio = Double.parseDouble(precioStr);
                    if (Validador.validarPositivo(precio)) {
                        libro.setPrecio(precio);
                    } else {
                        System.out.println("Error: El precio debe ser mayor que cero. No se actualizará este campo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Valor de precio no válido. No se actualizará este campo.");
                }
            }
            
            String stockStr = leerTextoOpcional("Stock [" + libro.getStock() + "]: ");
            if (!stockStr.isEmpty()) {
                try {
                    int stock = Integer.parseInt(stockStr);
                    if (Validador.validarNoNegativo(stock)) {
                        libro.setStock(stock);
                    } else {
                        System.out.println("Error: El stock no puede ser negativo. No se actualizará este campo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Valor de stock no válido. No se actualizará este campo.");
                }
            }
            
            System.out.println("Categoría actual: " + libro.getCategoriaNombre());
            String cambiarCategoria = leerTextoOpcional("¿Desea cambiar la categoría? (S/N): ");
            if (cambiarCategoria.equalsIgnoreCase("S")) {
                int categoriaId = seleccionarCategoria();
                if (categoriaId > 0) {
                    libro.setCategoriaId(categoriaId);
                }
            }
            
            if (libroDAO.actualizar(libro)) {
                System.out.println("¡Libro actualizado con éxito!");
            } else {
                System.out.println("Error al actualizar el libro.");
            }
        } else {
            System.out.println("No se encontró ningún libro con ID: " + id);
        }
    }
    
    /**
     * Actualiza el stock de un libro
     */
    private static void actualizarStock() {
        System.out.println("\n--- ACTUALIZAR STOCK ---");
        int id = leerEntero("Ingrese el ID del libro: ");
        
        Libro libro = libroDAO.obtenerPorId(id);
        
        if (libro != null) {
            System.out.println("Libro: " + libro.getTitulo());
            System.out.println("Stock actual: " + libro.getStock());
            
            int nuevoStock = leerEnteroNoNegativo("Nuevo stock: ");
            
            if (libroDAO.actualizarStock(id, nuevoStock)) {
                System.out.println("Stock actualizado con éxito.");
            } else {
                System.out.println("Error al actualizar el stock.");
            }
        } else {
            System.out.println("No se encontró ningún libro con ID: " + id);
        }
    }
    
    /**
     * Elimina un libro
     */
    private static void eliminarLibro() {
        System.out.println("\n--- ELIMINAR LIBRO ---");
        int id = leerEntero("Ingrese el ID del libro a eliminar: ");
        
        Libro libro = libroDAO.obtenerPorId(id);
        
        if (libro != null) {
            System.out.println("Libro encontrado: " + libro.getTitulo());
            String confirmar = leerTexto("¿Está seguro que desea eliminar este libro? (S/N): ");
            
            if (confirmar.equalsIgnoreCase("S")) {
                if (libroDAO.eliminar(id)) {
                    System.out.println("Libro eliminado con éxito.");
                } else {
                    System.out.println("Error al eliminar el libro.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("No se encontró ningún libro con ID: " + id);
        }
    }
    
    // ========== MÉTODOS PARA GESTIÓN DE CATEGORÍAS ==========
    
    /**
     * Crea una nueva categoría
     */
    private static void crearCategoria() {
        System.out.println("\n--- CREAR NUEVA CATEGORÍA ---");
        String nombre = leerTexto("Nombre de la categoría: ");
        
        // Verificar si ya existe una categoría con ese nombre
        if (categoriaDAO.existeNombre(nombre)) {
            System.out.println("Error: Ya existe una categoría con ese nombre.");
            return;
        }
        
        Categoria categoria = new Categoria(nombre);
        
        if (categoriaDAO.insertar(categoria)) {
            System.out.println("¡Categoría creada con éxito! ID: " + categoria.getId());
        } else {
            System.out.println("Error al crear la categoría.");
        }
    }
    
    /**
     * Lista todas las categorías
     */
    private static void listarCategorias() {
        System.out.println("\n--- LISTADO DE CATEGORÍAS ---");
        List<Categoria> categorias = categoriaDAO.obtenerTodos();
        
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
        } else {
            for (Categoria categoria : categorias) {
                System.out.println(categoria);
            }
            System.out.println("Total de categorías: " + categorias.size());
        }
    }
    
    /**
     * Actualiza una categoría
     */
    private static void actualizarCategoria() {
        System.out.println("\n--- ACTUALIZAR CATEGORÍA ---");
        int id = leerEntero("Ingrese el ID de la categoría a actualizar: ");
        
        Categoria categoria = categoriaDAO.obtenerPorId(id);
        
        if (categoria != null) {
            System.out.println("Categoría encontrada: " + categoria.getNombre());
            String nuevoNombre = leerTexto("Nuevo nombre: ");
            
            // Verificar si ya existe una categoría con ese nombre
            if (!nuevoNombre.equals(categoria.getNombre()) && categoriaDAO.existeNombre(nuevoNombre)) {
                System.out.println("Error: Ya existe una categoría con ese nombre.");
                return;
            }
            
            categoria.setNombre(nuevoNombre);
            
            if (categoriaDAO.actualizar(categoria)) {
                System.out.println("¡Categoría actualizada con éxito!");
            } else {
                System.out.println("Error al actualizar la categoría.");
            }
        } else {
            System.out.println("No se encontró ninguna categoría con ID: " + id);
        }
    }
    
    /**
     * Elimina una categoría
     */
    private static void eliminarCategoria() {
        System.out.println("\n--- ELIMINAR CATEGORÍA ---");
        int id = leerEntero("Ingrese el ID de la categoría a eliminar: ");
        
        Categoria categoria = categoriaDAO.obtenerPorId(id);
        
        if (categoria != null) {
            System.out.println("Categoría encontrada: " + categoria.getNombre());
            System.out.println("ADVERTENCIA: Si elimina esta categoría, los libros asociados quedarán sin categoría.");
            String confirmar = leerTexto("¿Está seguro que desea eliminar esta categoría? (S/N): ");
            
            if (confirmar.equalsIgnoreCase("S")) {
                if (categoriaDAO.eliminar(id)) {
                    System.out.println("Categoría eliminada con éxito.");
                } else {
                    System.out.println("Error al eliminar la categoría.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("No se encontró ninguna categoría con ID: " + id);
        }
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Selecciona una categoría de la lista de categorías disponibles
     * @return ID de la categoría seleccionada, 0 si no se selecciona ninguna
     */
    private static int seleccionarCategoria() {
        List<Categoria> categorias = categoriaDAO.obtenerTodos();
        
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías disponibles. Debe crear al menos una categoría.");
            return 0;
        }
        
        System.out.println("Categorías disponibles:");
        for (Categoria cat : categorias) {
            System.out.println(cat);
        }
        
        return leerEntero("Seleccione el ID de la categoría (0 para ninguna): ");
    }
    
    /**
     * Lee un ISBN válido
     * @return ISBN válido
     */
    private static String leerIsbn() {
        while (true) {
            String isbn = leerTexto("ISBN: ");
            
            // Validar formato
            if (!Validador.validarIsbn(isbn)) {
                System.out.println("Error: El ISBN debe tener 10 o 13 dígitos.");
                continue;
            }
            
            // Verificar si ya existe
            if (libroDAO.existeIsbn(isbn)) {
                System.out.println("Error: El ISBN ya está registrado en otro libro.");
                continue;
            }
            
            return isbn;
        }
    }
    
    /**
     * Lee un precio válido
     * @return Precio válido (mayor que cero)
     */
    private static double leerPrecio() {
        while (true) {
            try {
                double precio = Double.parseDouble(leerTexto("Precio: "));
                
                if (Validador.validarPositivo(precio)) {
                    return precio;
                } else {
                    System.out.println("Error: El precio debe ser mayor que cero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un valor numérico para el precio.");
            }
        }
    }
    
    /**
     * Lee una cadena de texto no vacía
     * @param mensaje Mensaje a mostrar
     * @return Texto ingresado
     */
    private static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            
            if (texto.isEmpty()) {
                System.out.println("Error: Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        
        return texto;
    }
    
    /**
     * Lee una cadena de texto opcional (puede estar vacía)
     * @param mensaje Mensaje a mostrar
     * @return Texto ingresado
     */
    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    
    /**
     * Lee un número entero
     * @param mensaje Mensaje a mostrar
     * @return Entero ingresado
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero.");
            }
        }
    }
    
    /**
     * Lee un número entero positivo (mayor que cero)
     * @param mensaje Mensaje a mostrar
     * @return Entero positivo ingresado
     */
    private static int leerEnteroPositivo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);
            
            if (Validador.validarPositivo(valor)) {
                return valor;
            } else {
                System.out.println("Error: El valor debe ser mayor que cero.");
            }
        }
    }
    
    /**
     * Lee un número entero no negativo (mayor o igual a cero)
     * @param mensaje Mensaje a mostrar
     * @return Entero no negativo ingresado
     */
    private static int leerEnteroNoNegativo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);
            
            if (Validador.validarNoNegativo(valor)) {
                return valor;
            } else {
                System.out.println("Error: El valor no puede ser negativo.");
            }
        }
    }
}