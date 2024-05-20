package Modelo;

public class Seccion implements Persistente{
    private int idSeccion;
    public static final String[] CAMPOS = {"idSeccion", "nombreSeccion", "descripcion", "idInstalacion"};

    public static String CAMPOS_SQL="`secciones`" +
            "(" +
            "`nombreSeccion`," +
            "`descripcion`," +
            "`idInstalacion`)" ;
    private String nombre;

    private String descripcion;

    private Instalacion instalacion;

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public Seccion(int idSeccion, String nombre, String descripcion, Instalacion instalacion) {
        this.idSeccion = idSeccion;
        this.nombre = nombre;
        this.descripcion = descripcion;

        this.instalacion = instalacion;
    }
    @Override
    public String getUpdateSQL(){
        return "secciones SET "+CAMPOS[1]+"='"+nombre+"' , "
                +CAMPOS[2]+"='"+descripcion+"' , "
                +CAMPOS[3]+"= "+instalacion.getIdInstalacion()
                +" WHERE "+CAMPOS[0]+"="+idSeccion;
    }

    @java.lang.Override
    public String toString() {
        return idSeccion+"-"+nombre.toUpperCase();
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    @Override
    public String toValoresSQL() {
        return
                "(" +
                        "'" + nombre +
                        "','" + descripcion  +
                        "',"+instalacion.getIdInstalacion()+")";
    }



}
