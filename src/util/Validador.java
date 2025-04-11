package util;

/**
 * Clase con métodos para validar datos de entrada
 */
public class Validador {
    
    /**
     * Valida que un String no sea nulo ni vacío
     * @param texto Texto a validar
     * @return true si es válido, false si es nulo o vacío
     */
    public static boolean validarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    
    /**
     * Valida que un número sea positivo
     * @param numero Número a validar
     * @return true si es positivo, false en caso contrario
     */
    public static boolean validarPositivo(double numero) {
        return numero > 0;
    }
    
    /**
     * Valida que un número sea mayor o igual a cero
     * @param numero Número a validar
     * @return true si es mayor o igual a cero, false en caso contrario
     */
    public static boolean validarNoNegativo(double numero) {
        return numero >= 0;
    }
    
    /**
     * Valida el formato de un ISBN
     * Esta implementación hace una validación básica:
     * - ISBN-10: 10 dígitos
     * - ISBN-13: 13 dígitos
     * @param isbn ISBN a validar
     * @return true si el formato es válido, false en caso contrario
     */
    public static boolean validarIsbn(String isbn) {
        if (isbn == null) {
            return false;
        }
        
        // Eliminar guiones y espacios
        String isbnLimpio = isbn.replaceAll("[\\-\\s]", "");
        
        // Verificar que solo contiene dígitos
        if (!isbnLimpio.matches("\\d+")) {
            return false;
        }
        
        // Verificar longitud (ISBN-10 o ISBN-13)
        return isbnLimpio.length() == 10 || isbnLimpio.length() == 13;
    }
}