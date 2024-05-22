package Modelo;

public class Instalacion implements Persistente {

    private String nombre;
    public static String CAMPOS_SQL="`instalaciones`" +
            "("+
            "`nombre`," +
            "`descripcion`)";
    private int idInstalacion;

    private String descripcion;

    public Instalacion(int idInstalacion,String name ,String descripcion) {
        this.nombre = name;
        this.idInstalacion = idInstalacion;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return idInstalacion+"-"+ nombre.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdInstalacion() {
        return idInstalacion;
    }

    public void setIdInstalacion(int idInstalacion) {
        this.idInstalacion = idInstalacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toValoresSQL() {
        return "('" + nombre +"','"+ descripcion + "')";
    }

    @Override
    public String getUpdateSQL() {
        return null;
    }
}
