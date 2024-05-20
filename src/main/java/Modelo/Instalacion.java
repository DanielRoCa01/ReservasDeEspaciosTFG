package Modelo;

public class Instalacion implements Persistente {

    private String name;
    public static String CAMPOS_SQL="`instalaciones`" +
            "("+
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
        return idInstalacion+"-"+name.toUpperCase();
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

    @Override
    public String toValoresSQL() {
        return "('" +name +"','"+ descripcion + "')";
    }

    @Override
    public String getUpdateSQL() {
        return null;
    }
}
