package Modelo;

public class Usuario {
    private int idUsuario;

    public static String CAMPOS_SQL="`usuarios`" +
            "(" +
            "`nombreUsuario`," +
            "`rol`," +
            "`seccion`," +
            "`idInstalacion`)" ;
    private String nombre;

    private String rol;

    private String seccion;

    private Instalacion instalacion;

    public Usuario(int idUsuario, String nombre, String rol, String seccion, Instalacion instalacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.seccion = seccion;
        this.instalacion = instalacion;
    }

    @java.lang.Override
    public String toString() {
        return
                "(" +
                "'" + nombre +
                "','" + rol  +
                "','" + seccion  +
               "',"+instalacion.getIdInstalacion()+")";
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
