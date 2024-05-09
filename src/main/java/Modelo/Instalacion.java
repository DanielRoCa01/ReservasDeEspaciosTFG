package Modelo;

public class Instalacion {

    private String name;
    public static String CAMPOS_SQL="`instalaciones`" +
            "(`idInstalacion`," +
            "`nombre`," +
            "`descripcion`)";
    private int idInstalacion;

    private String descripcion;

    public Instalacion(int idInstalacion,String name ,String descripcion) {
        this.name = name;
        this.idInstalacion = idInstalacion;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "('" +name +"','"+ descripcion + "')";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
