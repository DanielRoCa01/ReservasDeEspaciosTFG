package Modelo;

/**
 * Interfaz para todas las clase de datos que deban ser almacenadas
 * de manera persistente
 */
public interface Persistente {
    /**
     * Devuelve un string con el contenido de la clase en formato de insercion SQL
     * @return String
     */
    public String toValoresSQL();

    /**
     * Devuelve un string con el contenido de la clase en formato de update SQL
     * @return String
     */
    public String getUpdateSQL();
}
