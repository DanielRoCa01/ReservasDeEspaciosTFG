package Modelo;

/**
 * Interfaz para todas las clase de datos que deban ser almacenadas
 * de manera persistente
 */
public interface Persistente {
    public String toValoresSQL();
    public String getUpdateSQL();
}
