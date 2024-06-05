package Modelo;

import javafx.beans.property.*;
import java.sql.Time;
import java.util.List;

/**
 * Clase que modela los espacios dentro de una instalacion
 */
public class Espacio implements Persistente {

    public final static List<String> TAMAÑOS = List.of("XS", "S", "M", "L", "XL");//Lista de posibles tamaños del espacio

    // Atributos básicos
    private int idEspacio;
    private String nombre;
    private String tamaño;
    private Time horaApertura;
    private Time horaCierre;
    private String descripcion;
    private Instalacion instalacion;     //Instalacion a la que pertenece

    // Campos estáticos relacionados con la base de datos
    public static final String CAMPOS_SQL = "`espacios`(`nombre`,`tamaño`,`horaApertura`,`horaCierre`,`descripcion`,`idInstalacion`)"; //Campos de la clase para la insercion SQL
    public static final String[] CAMPOS = {"idEspacio", "nombre", "tamaño", "horaApertura", "horaCierre", "descripcion", "idInstalacion"};//Campos de la clase para SQL

    // Constructor que inicializa los atributos con los valores proporcionados
    public Espacio(int idEspacio, String nombre, String tamaño, Time horaApertura, Time horaCierre, String descripcion, Instalacion instalacion) {
        this.idEspacio = idEspacio;
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.descripcion = descripcion;
        this.instalacion = instalacion;
    }

    // Sobrescritura del método toString para una representación de cadena personalizada del objeto Espacio
    @Override
    public String toString() {
        return idEspacio + "-" + nombre.toUpperCase();
    }

    // Métodos getter y setter para idEspacio
    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    // Métodos getter y setter para nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método getter para tamaño
    public String getTamaño() {
        return tamaño;
    }

    // Método getter para horaApertura
    public Time getHoraApertura() {
        return horaApertura;
    }


    // Método getter para horaCierre
    public Time getHoraCierre() {
        return horaCierre;
    }



    // Métodos getter y setter para descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Métodos getter y setter para instalacion
    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    @Override
    public String toValoresSQL() {
        return "(" +
                "'" + nombre + '\'' +
                ",'" + tamaño + '\'' +
                ", '" + horaApertura + '\'' +
                ", '" + horaCierre + '\'' +
                ",'" + descripcion + '\'' +
                "," + instalacion.getIdInstalacion() +
                ')';
    }

    @Override
    public String getUpdateSQL() {
        return "espacios SET " + Espacio.CAMPOS[1] + "='" + nombre + "' , "
                + Espacio.CAMPOS[2] + "='" + tamaño + "' , "
                + Espacio.CAMPOS[3] + "= '" + horaApertura + "' , "
                + Espacio.CAMPOS[4] + "= '" + horaCierre + "' , "
                + Espacio.CAMPOS[5] + "= '" + descripcion + "' , "
                + Espacio.CAMPOS[6] + "= " + instalacion.getIdInstalacion() +
                " WHERE " + Espacio.CAMPOS[0] + "=" + idEspacio;
    }
}