package Modelo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que modela un usuario con un rol y seccion concreta dentro de una instalacion
 */
public class Usuario implements Persistente{
    private int idUsuario;

    public final static ArrayList<String> ROLES = new ArrayList<>(Arrays.asList("ADMINISTRADOR", "USUARIO"));//Posibles roles para un usuario
    public static final String[] CAMPOS = {"idUsuario", "nombreUsuario", "rol", "idSeccion", "idInstalacion"};//Campos de la clase para SQL
    public static String CAMPOS_SQL="`usuarios`" +
            "(" +
            "`nombreUsuario`," +
            "`rol`," +
            "`idSeccion`," +
            "`idInstalacion`)" ;//Campos de la clase para la insercion SQL
    private String nombre;

    private String rol;

    private Seccion seccion;

    private Instalacion instalacion; //Instalacion a la que pertenece

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public Usuario(int idUsuario, String nombre, String rol, Seccion seccion, Instalacion instalacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.seccion = seccion;
        this.instalacion = instalacion;
    }
    public String getUpdateSQL(){
        return "usuarios SET "+CAMPOS[1]+"='"+nombre+"' , "
                +CAMPOS[2]+"='"+rol+"' , "
                +CAMPOS[3]+"= "+seccion.getIdSeccion()+" , "
                +CAMPOS[4]+"= "+instalacion.getIdInstalacion()
                +" WHERE "+CAMPOS[0]+"="+idUsuario;
    }
    @java.lang.Override
    public String toString() {
        return idUsuario+"-"+nombre.toUpperCase();
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

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    @Override
    public String toValoresSQL() {
          return
                "(" +
                        "'" + nombre +
                        "','" + rol  +
                        "','" + seccion.getIdSeccion()  +
                        "',"+instalacion.getIdInstalacion()+")";
    }
}
