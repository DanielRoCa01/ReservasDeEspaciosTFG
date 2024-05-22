package Modelo;

import javafx.beans.property.*;
import java.sql.Time;
import java.util.List;

public class Espacio implements Persistente{

    public final static List<String> TAMAÑOS = List.of("XS", "S", "M", "L", "XL");
    // Propiedad observable para el ID del espacio
    private final IntegerProperty idEspacio = new SimpleIntegerProperty();

    // Campos estáticos relacionados con la base de datos, no necesitan ser propiedades observables
    public static final String CAMPOS_SQL = "`espacios`(`nombre`,`tamaño`,`horaApertura`,`horaCierre`,`descripcion`,`idInstalacion`)";
    public static final String[] CAMPOS = {"idEspacio", "nombre", "tamaño", "horaApertura", "horaCierre", "descripcion", "idInstalacion"};

    // Propiedades observables para los atributos del espacio
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty tamaño = new SimpleStringProperty();
    private final ObjectProperty<Time> horaApertura = new SimpleObjectProperty<>();
    private final ObjectProperty<Time> horaCierre = new SimpleObjectProperty<>();
    private final StringProperty descripcion = new SimpleStringProperty();
    private final ObjectProperty<Instalacion> instalacion = new SimpleObjectProperty<>();

    // Constructor que inicializa las propiedades con los valores proporcionados
    public Espacio(int idEspacio, String nombre, String tamaño, Time horaApertura, Time horaCierre, String descripcion, Instalacion instalacion) {
        this.idEspacio.set(idEspacio);
        this.nombre.set(nombre);
        this.tamaño.set(tamaño);
        this.horaApertura.set(horaApertura);
        this.horaCierre.set(horaCierre);
        this.descripcion.set(descripcion);
        this.instalacion.set(instalacion);
    }

    // Sobrescritura del método toString para una representación de cadena personalizada del objeto Espacio
    @Override
    public String toString() {
        return idEspacio.get()+"-"+nombre.get().toUpperCase();
    }

    // Métodos getter y setter para la propiedad idEspacio
    public int getIdEspacio() {
        return idEspacio.get();
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio.set(idEspacio);
    }

    public IntegerProperty idEspacioProperty() {
        return idEspacio;
    }

    // Métodos getter y setter para la propiedad nombre
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    // Métodos getter y setter para la propiedad tamaño
    public String getTamaño() {
        return tamaño.get();
    }

    public void setTamaño(String tamaño) {
        this.tamaño.set(tamaño);
    }

    public StringProperty tamañoProperty() {
        return tamaño;
    }

    // Métodos getter y setter para la propiedad horaApertura
    public Time getHoraApertura() {
        return horaApertura.get();
    }

    public void setHoraApertura(Time horaApertura) {
        this.horaApertura.set(horaApertura);
    }

    public ObjectProperty<Time> horaAperturaProperty() {
        return horaApertura;
    }

    // Métodos getter y setter para la propiedad horaCierre
    public Time getHoraCierre() {
        return horaCierre.get();
    }

    public void setHoraCierre(Time horaCierre) {
        this.horaCierre.set(horaCierre);
    }

    public ObjectProperty<Time> horaCierreProperty() {
        return horaCierre;
    }

    // Métodos getter y setter para la propiedad descripcion
    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    // Métodos getter y setter para la propiedad instalacion
    public Instalacion getInstalacion() {
        return instalacion.get();
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion.set(instalacion);
    }

    public ObjectProperty<Instalacion> instalacionProperty() {
        return instalacion;
    }

    @Override
    public String toValoresSQL() {
        return "(" +
                "'" + nombre.get() + '\'' +
                ",'" + tamaño.get() + '\'' +
                ", '" + horaApertura.get() + '\'' +
                ", '" + horaCierre.get() + '\'' +
                ",'" + descripcion.get() + '\'' +
                "," + instalacion.get().getIdInstalacion() +
                ')';
    }

    @Override
    public String getUpdateSQL() {
        return "espacios SET "+Espacio.CAMPOS[1]+"='"+nombre.get()+"' , "
                +Espacio.CAMPOS[2]+"='"+tamaño.get()+"' , "
                +Espacio.CAMPOS[3]+"= '"+horaApertura.get()+"' , "
                +Espacio.CAMPOS[4]+"= '"+horaCierre.get()+"' , "
                +Espacio.CAMPOS[5]+"= '"+descripcion.get()+"' , "
                +Espacio.CAMPOS[6]+"= "+instalacion.get().getIdInstalacion()+
                " WHERE "+Espacio.CAMPOS[0]+"="+idEspacio.get();
    }
}