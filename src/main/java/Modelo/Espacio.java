package Modelo;
import java.sql.Time;
import java.time.*;
public class Espacio {

    private int idEspacio;
    public static String CAMPOS_SQL="`espacios`(`nombre`,`tamaño`,`horaApertura`,`horaCierre`,`descripcion`,`idInstalacion`)";
    private String nombre;

    private String tamaño;

    private Time horaApertura;

    private Time horaCierre;

    private String descripcion;

    private Instalacion instalacion;

    public Espacio(int idEspacio, String nombre, String tamaño, Time horaApertura, Time horaCierre, String descripcion, Instalacion instalacion) {
        this.idEspacio = idEspacio;
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.descripcion = descripcion;
        this.instalacion = instalacion;
    }

    @java.lang.Override
    public String toString() {
        return
                "(" +
                "'" + nombre + '\'' +
                ",'" + tamaño + '\'' +
                ", '" + horaApertura + '\''+
                ", '" + horaCierre + '\''+
                ",'" + descripcion + '\'' +
                "," + instalacion.getIdInstalacion()+
                ')';
    }

    public int getidEspacio() {
        return idEspacio;
    }

    public void setidEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public Time getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(Time horaApertura) {
        this.horaApertura = horaApertura;
    }

    public Time getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Time  horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }
}
